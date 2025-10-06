package es.cheste.ClinicaDental.interfaces;

import es.cheste.ClinicaDental.entidades.Dentista;
import es.cheste.ClinicaDental.excepcion.DAOException;

import java.util.List;
import java.util.Optional;

public interface DentistaDAO {
    // Operaciones CRUD
    List<Dentista> obtenerTodos() throws DAOException;
    Optional<Dentista> obtenerPorId(Long id) throws DAOException;
    Dentista insertar(Dentista dentista) throws DAOException;
    Dentista actualizar(Dentista dentista) throws DAOException;
    void eliminar(Long id) throws DAOException;
}

