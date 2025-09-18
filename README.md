# 🩺 Sistema de Gestión de Citas Médicas

Este es un sistema de consola desarrollado en **Java**, diseñado para administrar citas en un consultorio médico. Permite gestionar doctores, pacientes y citas, con control de acceso para administradores. La información se guarda de forma persistente en archivos **JSON**.

---

##  Características

- **Gestión de Doctores**  
  Alta y listado de doctores con su especialidad.

- **Gestión de Pacientes**  
  Registro y listado de pacientes del consultorio.

- **Gestión de Citas**  
  Creación de citas asignando doctor, paciente, fecha, hora y motivo.

- **Persistencia de Datos**  
  Toda la información se guarda en archivos `.json` dentro de la carpeta `db`.

- **Control de Acceso**  
  Sistema de login para administradores con usuario y contraseña.

---
#  Instalación 

Este sistema se distribuye como un archivo ejecutable `.jar`, lo que permite una instalación rápida y sin complicaciones. No necesitas compilar el código fuente: simplemente descarga, ejecuta y comienza a usarlo.

---

##  Requisito Único

Para ejecutar el programa, necesitas tener instalado **Java Runtime Environment (JRE)** versión **8 o superior**.

Puedes verificar tu versión de Java abriendo una terminal y ejecutando:

java -version

## Ejecuta el programa

Abre una **terminal** o **símbolo del sistema**, navega hasta la carpeta donde se encuentra el archivo `.jar` y ejecuta el siguiente comando:

java -jar Citas.jar

## ▶️ Uso del Programa

### Inicio de Sesión

Al iniciar el programa, se solicitarán credenciales. En la primera ejecución, utiliza los datos por defecto:

- **Usuario:** `admin`  
- **Contraseña:** `1234`

---

### Navegación por el Menú

Una vez dentro, tendrás acceso a las siguientes opciones:

1.  **Dar de alta un Doctor**  
   Registra un nuevo profesional médico.

2. **Dar de alta un Paciente**  
   Registra un nuevo paciente.

3. **Crear una Cita**  
   Agenda una cita entre un doctor y un paciente existentes.

4. **Ver todas las Citas**  
   Muestra un listado detallado de todas las citas, ordenadas por fecha.

5. **Salir**  
   Finaliza la ejecución del programa.

---

## Créditos

Este proyecto fue creado por:  
**Yaret Velazquez**

