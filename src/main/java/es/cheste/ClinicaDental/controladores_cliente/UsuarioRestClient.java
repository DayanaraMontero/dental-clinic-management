package es.cheste.ClinicaDental.controladores_cliente;

import es.cheste.ClinicaDental.entidades.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UsuarioRestClient {
    private final RestTemplate restTemplate;

    @Value("${api.usuario.url}")
    private String apiUrl;

    public UsuarioRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Usuario> listarUsuarios() {
        Usuario[] usuariosArray = restTemplate.getForObject(apiUrl, Usuario[].class);
        return usuariosArray != null ? Arrays.asList(usuariosArray) : List.of();
    }

    public Usuario obtenerPorId(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, Usuario.class);
    }

    public void agregarUsuario(Usuario usuario) {
        restTemplate.postForObject(apiUrl, usuario, Usuario.class);
    }

    public void actualizarUsuario(Usuario usuario) {
        restTemplate.put(apiUrl + "/" + usuario.getId(), usuario);
    }

    public void eliminarUsuario(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }

    // Método para registrar un usuario (sin TipoUsuario)
    public void registrarUsuario(String nombre, String email, String contrasenya) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContrasenya(contrasenya);

        restTemplate.postForObject(apiUrl, usuario, Void.class);
    }

    // Método para login de usuario
    public Usuario login(String email, String contrasenya) {
        Map<String, String> credenciales = new HashMap<>();
        credenciales.put("email", email);
        credenciales.put("contrasenya", contrasenya);

        return restTemplate.postForObject(apiUrl + "/login", credenciales, Usuario.class);
    }
}

