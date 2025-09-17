// Cita.java
import java.time.LocalDateTime;

public class Cita {
    private String idCita;
    private String idDoctor;
    private String idPaciente;
    private LocalDateTime fechaHora;
    private String motivo;

    // Constructor
    public Cita(String idCita, String idDoctor, String idPaciente, LocalDateTime fechaHora, String motivo) {
        this.idCita = idCita;
        this.idDoctor = idDoctor;
        this.idPaciente = idPaciente;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
    }

    // Getters
    public String getIdCita() {
        return idCita;
    }

    public String getIdDoctor() {
        return idDoctor;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }
}