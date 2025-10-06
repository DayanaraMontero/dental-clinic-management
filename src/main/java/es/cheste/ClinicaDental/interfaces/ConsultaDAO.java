package es.cheste.ClinicaDental.interfaces;

import es.cheste.ClinicaDental.entidades.Consulta;
import es.cheste.ClinicaDental.excepcion.DAOException;

import java.util.List;
import java.util.Optional;

public interface ConsultaDAO {
    // Operaciones CRUD
    List<Consulta> obtenerTodos() throws DAOException;
    Optional<Consulta> obtenerPorId(Long id) throws DAOException;
    Consulta insertar(Consulta sala) throws DAOException;
    Consulta actualizar(Consulta sala) throws DAOException;
    void eliminar(Long id) throws DAOException;
}
