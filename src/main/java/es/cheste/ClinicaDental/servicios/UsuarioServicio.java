package es.cheste.ClinicaDental.servicios;

import es.cheste.ClinicaDental.entidades.Usuario;
import es.cheste.ClinicaDental.excepcion.DAOException;
import es.cheste.ClinicaDental.interfaces.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {
    private final UsuarioDAO usuarioDAO;

    @Autowired
    public UsuarioServicio(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario crearUsuario(Usuario usuario) throws DAOException {
        try {
            return usuarioDAO.insertar(usuario);
        } catch (DAOException e) {
            throw new DAOException("Error al insertar al usuario", e);
        }
    }

    public Optional<Usuario> obtenerUsuario(Long id) throws DAOException {
        try {
            return usuarioDAO.obtenerPorId(id);
        } catch (DAOException e) {
            throw new DAOException("Error al obtener usuario con ID: " + id, e);
        }
    }

    public List<Usuario> listarUsuarios() throws DAOException {
        try {
            return usuarioDAO.obtenerTodos();
        } catch (DAOException e) {
            throw new DAOException("Error al obtener todos los usuarios", e);
        }
    }

    public Usuario actualizarUsuario(Usuario usuario) throws DAOException {
        try {
            return usuarioDAO.actualizar(usuario);
        } catch (DAOException e) {
            throw new DAOException("Error al actualizar el usuario", e);
        }
    }

    public void eliminarUsuario(Long id) throws DAOException {
        Optional<Usuario> usuario = usuarioDAO.obtenerPorId(id);
        if (usuario.isPresent()) {
            try {
                usuarioDAO.eliminar(id);
            } catch (DAOException e) {
                throw new DAOException("Error al eliminar usuario con ID: " + id, e);
            }

        } else {
            throw new DAOException("Usuario con ID " + id + " no encontrado", null);
        }
    }

    public Usuario login(String email, String contrasenya) throws DAOException {
        try {
            Optional<Usuario> usuarioOpt = usuarioDAO.obtenerPorEmail(email);
            if (usuarioOpt.isEmpty()) {
                throw new DAOException("Credenciales incorrectas", null);
            }

            Usuario usuario = usuarioOpt.get();

            if (!usuario.getContrasenya().equals(contrasenya)) {
                throw new DAOException("Credenciales incorrectas", null);
            }

            return usuario;
        } catch (DAOException e) {
            throw new DAOException("Error al iniciar sesión", e);
        }
    }
}
