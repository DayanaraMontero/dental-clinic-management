package es.cheste.ClinicaDental.interfaces;

import es.cheste.ClinicaDental.entidades.Factura;
import es.cheste.ClinicaDental.excepcion.DAOException;

import java.util.List;
import java.util.Optional;

public interface FacturaDAO {
    // Operaciones CRUD
    List<Factura> obtenerTodos() throws DAOException;
    Optional<Factura> obtenerPorId(Long id) throws DAOException;
    Factura insertar(Factura factura) throws DAOException;
    Factura actualizar(Factura factura) throws DAOException;
    void eliminar(Long id) throws DAOException;
}
