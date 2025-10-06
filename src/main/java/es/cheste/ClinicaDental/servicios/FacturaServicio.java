package es.cheste.ClinicaDental.servicios;

import es.cheste.ClinicaDental.entidades.Factura;
import es.cheste.ClinicaDental.excepcion.DAOException;
import es.cheste.ClinicaDental.interfaces.FacturaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaServicio {
    private final FacturaDAO facturaDAO;

    @Autowired
    public FacturaServicio(FacturaDAO facturaDAO) {
        this.facturaDAO = facturaDAO;
    }

    public Factura crearFactura(Factura factura) throws DAOException {
        try {
            return facturaDAO.insertar(factura);
        } catch (DAOException e) {
            throw new DAOException("Error al insertar la factura", e);
        }
    }

    public Optional<Factura> obtenerFactura(Long id) throws DAOException {
        try {
            return facturaDAO.obtenerPorId(id);
        } catch (DAOException e) {
            throw new DAOException("Error al obtener factura con ID: " + id, e);
        }
    }

    public List<Factura> listarFacturas() throws DAOException {
        try {
            return facturaDAO.obtenerTodos();
        } catch (DAOException e) {
            throw new DAOException("Error al obtener todas las facturas", e);
        }
    }

    public Factura actualizarFactura(Factura factura) throws DAOException {
        try {
            return facturaDAO.actualizar(factura);
        } catch (DAOException e) {
            throw new DAOException("Error al actualizar la factura", e);
        }
    }

    public void eliminarFactura(Long id) throws DAOException {
        Optional<Factura> factura = facturaDAO.obtenerPorId(id);
        if (factura.isPresent()) {
            try {
                facturaDAO.eliminar(id);
            } catch (DAOException e) {
                throw new DAOException("Error al eliminar factura con ID: " + id, e);
            }

        } else {
            throw new DAOException("Factura con ID " + id + " no encontrado", null);
        }
    }
}
