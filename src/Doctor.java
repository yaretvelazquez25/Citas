public class Doctor {
    private String id;
    private String nombreCompleto;
    private String especialidad;

    // Constructor
    public Doctor(String id, String nombreCompleto, String especialidad) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
    }

    public String getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    @Override
    public String toString() {
        return "ID: " + id + " - Nombre: " + nombreCompleto + " (" + especialidad + ")";
    }
}