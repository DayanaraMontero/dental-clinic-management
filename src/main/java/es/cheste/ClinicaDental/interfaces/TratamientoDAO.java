package es.cheste.ClinicaDental.interfaces;

import es.cheste.ClinicaDental.entidades.Tratamiento;
import es.cheste.ClinicaDental.excepcion.DAOException;

import java.util.List;
import java.util.Optional;

public interface TratamientoDAO {
    // Operaciones CRUD
    List<Tratamiento> obtenerTodos() throws DAOException;
    Optional<Tratamiento> obtenerPorId(Long id) throws DAOException;
    Tratamiento insertar(Tratamiento tratamiento) throws DAOException;
    Tratamiento actualizar(Tratamiento tratamiento) throws DAOException;
    void eliminar(Long id) throws DAOException;
}
