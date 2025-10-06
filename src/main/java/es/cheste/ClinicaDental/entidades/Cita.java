package es.cheste.ClinicaDental.entidades;

import es.cheste.ClinicaDental.enums.EstadoCita;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "cita")
public class Cita implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false, foreignKey = @ForeignKey(name = "FK_CITA_PACIENTE"))
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_dentista", nullable = false, foreignKey = @ForeignKey(name = "FK_CITA_DENTISTA"))
    private Dentista dentista;

    @ManyToOne
    @JoinColumn(name = "id_sala", nullable = false, foreignKey = @ForeignKey(name = "FK_CITA_SALA"))
    private Consulta sala;

    @ManyToOne
    @JoinColumn(name = "id_tratamiento", nullable = false, foreignKey = @ForeignKey(name = "FK_CITA_TRATAMIENTO"))
    private Tratamiento tratamiento;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoCita estadoCita;

    @Column(name = "observaciones", length = 150)
    private String observaciones;

    public Cita() { }

    public Cita(Long id, LocalDate fecha, Paciente paciente, Dentista dentista, Consulta sala, Tratamiento tratamiento, EstadoCita estadoCita, String observaciones) {
        this.id = id;
        this.fecha = fecha;
        this.paciente = paciente;
        this.dentista = dentista;
        this.sala = sala;
        this.tratamiento = tratamiento;
        this.estadoCita = estadoCita;
        this.observaciones = observaciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public Consulta getSala() {
        return sala;
    }

    public void setSala(Consulta sala) {
        this.sala = sala;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public EstadoCita getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(EstadoCita estadoCita) {
        this.estadoCita = estadoCita;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", paciente=" + paciente +
                ", dentista=" + dentista +
                ", sala=" + sala +
                ", tratamiento=" + tratamiento +
                ", estadoCita=" + estadoCita +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cita cita = (Cita) o;
        return Objects.equals(id, cita.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
