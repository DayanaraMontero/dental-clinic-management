package es.cheste.ClinicaDental.controladores_rest;

import es.cheste.ClinicaDental.entidades.Usuario;
import es.cheste.ClinicaDental.excepcion.DAOException;
import es.cheste.ClinicaDental.servicios.UsuarioServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {
    private final UsuarioServicio usuarioServicio;

    public UsuarioController(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping("usuario")
    public ResponseEntity<?> create(@RequestBody Usuario usuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            Usuario nuevoUsuario = usuarioServicio.crearUsuario(usuario);
            response.put("mensaje", "Usuario creado con exito");
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (DAOException e) {
            response.put("mensaje", "Error al crear el usuario: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("usuario/{id}")
    public ResponseEntity<?> update(@RequestBody Usuario usuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            Usuario usuarioActualizado = usuarioServicio.actualizarUsuario(usuario);
            response.put("mensaje", "Usuario actualizado con exito");
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } catch (DAOException e) {
            response.put("mensaje", "Error al actualizar el usuario: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("usuario/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            usuarioServicio.eliminarUsuario(id);
            response.put("mensaje", "Usuario eliminado con exito");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DAOException e) {
            System.out.println("Eliminar Usuario: " + e.getMessage());
            response.put("mensaje", "Error al eliminar el usuario: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("usuario/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Usuario> usuario = usuarioServicio.obtenerUsuario(id);
            if (usuario.isPresent()) {
                response.put("mensaje", "Usuario encontrado con ID: " + id);
                return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
            } else {
                response.put("mensaje", "Usuario no encontrado con ID: " + id);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (DAOException e) {
            response.put("mensaje", "Error al obtener el usuario: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("usuario")
    public ResponseEntity<?> findAll() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Usuario> usuarios = usuarioServicio.listarUsuarios();
            response.put("mensaje", "Usuarios encontrados con exito");
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (DAOException e) {
            response.put("mensaje", "Error al obtener los usuarios: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/usuario/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        Map<String, Object> response = new HashMap<>();
        try {
            String email = credenciales.get("email");
            String contrasenya = credenciales.get("contrasenya");

            Usuario usuario = usuarioServicio.login(email, contrasenya);
            response.put("mensaje", "Login exitoso");
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "Error al iniciar sesión: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
