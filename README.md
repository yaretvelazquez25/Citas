# Sistema de Gestión de Citas Médicas
 Este es un sistema de consola simple, desarrollado en Java, para la administración de citas en un consultorio médico. Permite gestionar doctores, pacientes y citas, con control de acceso para administradores. La información se almacena de forma persistente en archivos JSON.

## Características 
Gestión de Doctores: Dar de alta y listar doctores con su especialidad.

Gestión de Pacientes: Registrar y listar pacientes del consultorio.

Gestión de Citas: Crear citas asignando un doctor y un paciente, con fecha, hora y motivo.

Persistencia de Datos: Toda la información se guarda en archivos .json en una carpeta db.

Control de Acceso: Sistema de login para administradores con usuario y contraseña
##Uso del Programa
Inicio de Sesión:
El programa te pedirá credenciales. La primera vez que lo ejecutes, utiliza los datos por defecto:

Usuario: admin

Contraseña: 1234

Navegación por el Menú:
Tras iniciar sesión, verás un menú con las siguientes opciones:

Dar de alta un Doctor: Registra un nuevo profesional médico.

Dar de alta un Paciente: Registra un nuevo paciente.

Crear una Cita: Agenda una nueva cita, asociando un doctor y un paciente existentes.

Ver todas las Citas: Muestra un listado detallado de todas las citas agendadas, ordenadas por fecha.

Salir: Termina la ejecución del programa.

##Créditos
Este proyecto fue creado por:
Yaret Velazquez
