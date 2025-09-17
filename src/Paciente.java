public class Paciente {
    private String id;
    private String nombreCompleto;

    public Paciente(String id, String nombreCompleto) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
    }

    public String getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    @Override
    public String toString() {
        return "ID: " + id + " - Nombre: " + nombreCompleto;
    }
}