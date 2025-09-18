// SistemaCitas.java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class SistemaCitas {

    private static final String DB_DIR = "db";
    private static final String DOCTORS_FILE = DB_DIR + "/doctores.json";
    private static final String PATIENTS_FILE = DB_DIR + "/pacientes.json";
    private static final String APPOINTMENTS_FILE = DB_DIR + "/citas.json";
    private static final String ADMINS_FILE = DB_DIR + "/admins.json";

    private static final Scanner scanner = new Scanner(System.in);
    private static final Gson gson;

    // Bloque estático para configurar Gson con soporte para LocalDateTime
    static {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Serializador: Convierte LocalDateTime a String
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) ->
                jsonSerializationContext.serialize(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        );

        // Deserializador: Convierte String a LocalDateTime
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );

        gson = gsonBuilder.setPrettyPrinting().create();
    }

    public static void main(String[] args) {
        inicializarSistema();
        if (login()) {
            mostrarMenuPrincipal();
        }
        scanner.close();
    }

    private static void inicializarSistema() {
        try {
            Files.createDirectories(Paths.get(DB_DIR));
            crearArchivoSiNoExiste(DOCTORS_FILE, "[]");
            crearArchivoSiNoExiste(PATIENTS_FILE, "[]");
            crearArchivoSiNoExiste(APPOINTMENTS_FILE, "[]");

            if (!new File(ADMINS_FILE).exists()) {
                // Usuario por defecto: admin, Contraseña por defecto: 1234
                List<Admin> admins = new ArrayList<>();
                admins.add(new Admin("admin", "1234"));
                guardarDatos(ADMINS_FILE, admins);
            }
        } catch (IOException e) {
            System.err.println("Error al inicializar el sistema: " + e.getMessage());
        }
    }

    private static void crearArchivoSiNoExiste(String filePath, String content) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }
        }
    }

    private static <T> List<T> cargarDatos(String archivo, Type tipo) {
        try (Reader reader = new FileReader(archivo)) {
            List<T> datos = gson.fromJson(reader, tipo);
            return datos == null ? new ArrayList<>() : datos;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static void guardarDatos(String archivo, Object datos) {
        try (Writer writer = new FileWriter(archivo)) {
            gson.toJson(datos, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos en " + archivo + ": " + e.getMessage());
        }
    }

    private static boolean login() {
        System.out.println("--- Inicio de Sesión ---");
        Type tipoListaAdmins = new TypeToken<ArrayList<Admin>>() {}.getType();
        List<Admin> admins = cargarDatos(ADMINS_FILE, tipoListaAdmins);

        for (int i = 0; i < 3; i++) {
            System.out.print("Usuario: ");
            String usuario = scanner.nextLine();
            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            for (Admin admin : admins) {
                if (admin.getIdAdmin().equals(usuario) && admin.getPassword().equals(password)) {
                    System.out.println("\n¡Inicio de sesión exitoso!");
                    return true;
                }
            }
            System.out.println("Usuario o contraseña incorrectos. Intentos restantes: " + (2 - i));
        }
        System.out.println("Has excedido el número de intentos.");
        return false;
    }

    private static void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n--- Sistema de Administración de Citas ---");
            System.out.println("1. Dar de alta un Doctor");
            System.out.println("2. Dar de alta un Paciente");
            System.out.println("3. Crear una Cita");
            System.out.println("4. Ver todas las Citas");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1": altaDoctor(); break;
                case "2": altaPaciente(); break;
                case "3": crearCita(); break;
                case "4": listarCitas(); break;
                case "5": System.out.println("Saliendo del sistema. ¡Hasta pronto!"); return;
                default: System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        }
    }

    private static void altaDoctor() {
        System.out.println("\n--- Alta de Doctor ---");
        System.out.print("Nombre completo del doctor: ");
        String nombre = scanner.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        Type tipoListaDoctores = new TypeToken<ArrayList<Doctor>>() {}.getType();
        List<Doctor> doctores = cargarDatos(DOCTORS_FILE, tipoListaDoctores);

        Doctor nuevoDoctor = new Doctor(UUID.randomUUID().toString(), nombre, especialidad);
        doctores.add(nuevoDoctor);
        guardarDatos(DOCTORS_FILE, doctores);

        System.out.println("\n¡Doctor '" + nombre + "' registrado con éxito!");
    }

    private static void altaPaciente() {
        System.out.println("\n--- Alta de Paciente ---");
        System.out.print("Nombre completo del paciente: ");
        String nombre = scanner.nextLine();

        Type tipoListaPacientes = new TypeToken<ArrayList<Paciente>>() {}.getType();
        List<Paciente> pacientes = cargarDatos(PATIENTS_FILE, tipoListaPacientes);

        Paciente nuevoPaciente = new Paciente(UUID.randomUUID().toString(), nombre);
        pacientes.add(nuevoPaciente);
        guardarDatos(PATIENTS_FILE, pacientes);

        System.out.println("\n¡Paciente '" + nombre + "' registrado con éxito!");
    }

    private static void crearCita() {
        System.out.println("\n--- Crear Nueva Cita ---");

        // Cargar y mostrar doctores
        Type tipoListaDoctores = new TypeToken<ArrayList<Doctor>>() {}.getType();
        List<Doctor> doctores = cargarDatos(DOCTORS_FILE, tipoListaDoctores);
        if (doctores.isEmpty()) {
            System.out.println("No hay doctores registrados. Agregue uno primero.");
            return;
        }
        System.out.println("\nDoctores disponibles:");
        doctores.forEach(System.out::println);
        System.out.print("Introduce el ID del doctor: ");
        String idDoctor = scanner.nextLine();
        if (doctores.stream().noneMatch(d -> d.getId().equals(idDoctor))) {
            System.out.println("ID de doctor no válido.");
            return;
        }

        // Cargar y mostrar pacientes
        Type tipoListaPacientes = new TypeToken<ArrayList<Paciente>>() {}.getType();
        List<Paciente> pacientes = cargarDatos(PATIENTS_FILE, tipoListaPacientes);
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados. Agregue uno primero.");
            return;
        }
        System.out.println("\nPacientes registrados:");
        pacientes.forEach(System.out::println);
        System.out.print("Introduce el ID del paciente: ");
        String idPaciente = scanner.nextLine();
        if (pacientes.stream().noneMatch(p -> p.getId().equals(idPaciente))) {
            System.out.println("ID de paciente no válido.");
            return;
        }

        // Solicitar fecha y hora
        LocalDateTime fechaHora = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        while (fechaHora == null) {
            System.out.print("Fecha y hora de la cita (formato YYYY-MM-DD HH:MM): ");
            String fechaStr = scanner.nextLine();
            try {
                fechaHora = LocalDateTime.parse(fechaStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha u hora incorrecto. Inténtalo de nuevo.");
            }
        }

        System.out.print("Motivo de la cita: ");
        String motivo = scanner.nextLine();

        Type tipoListaCitas = new TypeToken<ArrayList<Cita>>() {}.getType();
        List<Cita> citas = cargarDatos(APPOINTMENTS_FILE, tipoListaCitas);

        Cita nuevaCita = new Cita(UUID.randomUUID().toString(), idDoctor, idPaciente, fechaHora, motivo);
        citas.add(nuevaCita);
        guardarDatos(APPOINTMENTS_FILE, citas);

        System.out.println("\n¡Cita creada con éxito!");
    }

    private static void listarCitas() {
        System.out.println("\n--- Listado de Todas las Citas ---");

        Type tipoListaCitas = new TypeToken<ArrayList<Cita>>() {}.getType();
        List<Cita> citas = cargarDatos(APPOINTMENTS_FILE, tipoListaCitas);

        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }

        Type tipoListaDoctores = new TypeToken<ArrayList<Doctor>>() {}.getType();
        List<Doctor> doctores = cargarDatos(DOCTORS_FILE, tipoListaDoctores);
        Map<String, Doctor> mapaDoctores = new HashMap<>();
        for(Doctor d : doctores) {
            mapaDoctores.put(d.getId(), d);
        }

        Type tipoListaPacientes = new TypeToken<ArrayList<Paciente>>() {}.getType();
        List<Paciente> pacientes = cargarDatos(PATIENTS_FILE, tipoListaPacientes);
        Map<String, Paciente> mapaPacientes = new HashMap<>();
        for(Paciente p : pacientes) {
            mapaPacientes.put(p.getId(), p);
        }

        citas.sort(Comparator.comparing(Cita::getFechaHora));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy 'a las' HH:mm", new Locale("es", "ES"));

        for (Cita cita : citas) {
            Doctor doctor = mapaDoctores.getOrDefault(cita.getIdDoctor(), new Doctor("?", "No encontrado", ""));
            Paciente paciente = mapaPacientes.getOrDefault(cita.getIdPaciente(), new Paciente("?", "No encontrado"));

            System.out.println("----------------------------------------");
            System.out.println("ID Cita: " + cita.getIdCita());
            System.out.println("Fecha y Hora: " + cita.getFechaHora().format(formatter));
            System.out.println("Paciente: " + paciente.getNombreCompleto());
            System.out.println("Doctor: " + doctor.getNombreCompleto() + " (" + doctor.getEspecialidad() + ")");
            System.out.println("Motivo: " + cita.getMotivo());
        }
        System.out.println("----------------------------------------");
    }
}