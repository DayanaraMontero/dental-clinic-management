package es.cheste.ClinicaDental.interfaces;

import es.cheste.ClinicaDental.entidades.Usuario;
import es.cheste.ClinicaDental.excepcion.DAOException;

import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {
    // Operaciones CRUD
    List<Usuario> obtenerTodos() throws DAOException;
    Optional<Usuario> obtenerPorId(Long id) throws DAOException;
    Usuario insertar(Usuario usuario) throws DAOException;
    Usuario actualizar(Usuario usuario) throws DAOException;
    void eliminar(Long id) throws DAOException;
    Optional<Usuario> obtenerPorEmail(String email) throws DAOException;
}
