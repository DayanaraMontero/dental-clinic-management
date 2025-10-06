package es.cheste.ClinicaDental.interfaces;

import es.cheste.ClinicaDental.entidades.Paciente;
import es.cheste.ClinicaDental.excepcion.DAOException;

import java.util.List;
import java.util.Optional;

public interface PacienteDAO {
    // Operaciones CRUD
    List<Paciente> obtenerTodos() throws DAOException;
    Optional<Paciente> obtenerPorId(Long id) throws DAOException;
    Paciente insertar(Paciente paciente) throws DAOException;
    Paciente actualizar(Paciente paciente) throws DAOException;
    void eliminar(Long id) throws DAOException;
}
