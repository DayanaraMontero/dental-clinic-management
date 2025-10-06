package es.cheste.ClinicaDental.entidades;

import es.cheste.ClinicaDental.enums.EstadoFactura;
import es.cheste.ClinicaDental.enums.MetodoPago;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "factura")
public class Factura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false, foreignKey = @ForeignKey(name = "FK_FACTURA_PACIENTE"))
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_tratamiento", nullable = false, foreignKey = @ForeignKey(name = "FK_FACTURA_TRATAMIENTO"))
    private Tratamiento tratamiento;

    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoFactura estadoFactura = EstadoFactura.PENDIENTE; // Default

    @Column(name = "metodo_pago", nullable = false)
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    public Factura() { }

    public Factura(Long id, Paciente paciente, Tratamiento tratamiento, BigDecimal total, LocalDate fecha, EstadoFactura estadoFactura, MetodoPago metodoPago) {
        this.id = id;
        this.paciente = paciente;
        this.tratamiento = tratamiento;
        this.total = total;
        this.fecha = fecha;
        this.estadoFactura = estadoFactura;
        this.metodoPago = metodoPago;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EstadoFactura getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(EstadoFactura estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", paciente=" + paciente +
                ", tratamiento=" + tratamiento +
                ", total=" + total +
                ", fecha=" + fecha +
                ", estadoFactura=" + estadoFactura +
                ", metodoPago=" + metodoPago +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factura factura = (Factura) o;
        return Objects.equals(id, factura.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
