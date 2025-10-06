package es.cheste.ClinicaDental.entidades;

import es.cheste.ClinicaDental.enums.EstadoTratamiento;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tratamiento")
public class Tratamiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tratamiento")
    private Long id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false, foreignKey = @ForeignKey(name = "FK_TRATAMIENTO_PACIENTE"))
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_dentista", nullable = false, foreignKey = @ForeignKey(name = "FK_TRATAMIENTO_DENTISTA"))
    private Dentista dentista;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoTratamiento estadoTratamiento;

    @Column(name = "observaciones", length = 150)
    private String observaciones;

    public Tratamiento() { }

    public Tratamiento(Long id, String nombre, Paciente paciente, Dentista dentista, LocalDate fecha, EstadoTratamiento estadoTratamiento, String observaciones) {
        this.id = id;
        this.nombre = nombre;
        this.paciente = paciente;
        this.dentista = dentista;
        this.fecha = fecha;
        this.estadoTratamiento = estadoTratamiento;
        this.observaciones = observaciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EstadoTratamiento getEstadoTratamiento() {
        return estadoTratamiento;
    }

    public void setEstadoTratamiento(EstadoTratamiento estadoTratamiento) {
        this.estadoTratamiento = estadoTratamiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Tratamiento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", paciente=" + paciente +
                ", dentista=" + dentista +
                ", fecha=" + fecha +
                ", estadoTratamiento=" + estadoTratamiento +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tratamiento that = (Tratamiento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
