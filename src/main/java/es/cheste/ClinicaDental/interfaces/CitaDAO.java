package es.cheste.ClinicaDental.interfaces;

import es.cheste.ClinicaDental.entidades.Cita;
import es.cheste.ClinicaDental.excepcion.DAOException;

import java.util.List;
import java.util.Optional;

public interface CitaDAO {
    // Operaciones CRUD
    List<Cita> obtenerTodos() throws DAOException;
    Optional<Cita> obtenerPorId(Long id) throws DAOException;
    Cita insertar(Cita cita) throws DAOException;
    Cita actualizar(Cita cita) throws DAOException;
    void eliminar(Long id) throws DAOException;
}
