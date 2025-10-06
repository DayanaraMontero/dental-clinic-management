package es.cheste.ClinicaDental.entidades;

import es.cheste.ClinicaDental.enums.EstadoConsulta;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "consulta")
public class Consulta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private Long id;

    @Column(name = "num_sala", nullable = false)
    private Integer numSala;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoConsulta estadoSala;

    @Column(name = "ubicacion", length = 100)
    private String ubicacion;

    public Consulta() { }

    public Consulta(Long id, Integer numSala, String nombre, EstadoConsulta estadoSala, String ubicacion) {
        this.id = id;
        this.numSala = numSala;
        this.nombre = nombre;
        this.estadoSala = estadoSala;
        this.ubicacion = ubicacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumSala() {
        return numSala;
    }

    public void setNumSala(Integer numSala) {
        this.numSala = numSala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadoConsulta getEstadoSala() {
        return estadoSala;
    }

    public void setEstadoSala(EstadoConsulta estadoSala) {
        this.estadoSala = estadoSala;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "id=" + id +
                ", numSala=" + numSala +
                ", nombre='" + nombre + '\'' +
                ", estadoSala=" + estadoSala +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consulta sala = (Consulta) o;
        return Objects.equals(id, sala.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
