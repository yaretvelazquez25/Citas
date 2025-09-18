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

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) ->
                jsonSerializationContext.serialize(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        );

        gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );

        gson = gsonBuilder.setPrettyPrinting().create();
    }

    public static void main(String[] args) {
        inicializarSistema();
        if (login()) {

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


}