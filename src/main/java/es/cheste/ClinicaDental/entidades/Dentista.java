package es.cheste.ClinicaDental.entidades;

import es.cheste.ClinicaDental.enums.Especialidad;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "dentista")
public class Dentista implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dentista")
    private Long id;

    @Column(name = "num_colegiado", unique = true, nullable = false)
    private Integer numColegiado;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 75, nullable = false)
    private String apellido;

    @Column(name = "especialidad", nullable = false)
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Column(name = "telefono", length = 9, nullable = false)
    private String telefono;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    public Dentista() { }

    public Dentista(Long id, Integer numColegiado, String nombre, String apellido, Especialidad especialidad, String telefono, String email) {
        this.id = id;
        this.numColegiado = numColegiado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumColegiado() {
        return numColegiado;
    }

    public void setNumColegiado(Integer numColegiado) {
        this.numColegiado = numColegiado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Dentista{" +
                "id=" + id +
                ", numColegiado=" + numColegiado +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", especialidad=" + especialidad +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dentista dentista = (Dentista) o;
        return Objects.equals(id, dentista.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

