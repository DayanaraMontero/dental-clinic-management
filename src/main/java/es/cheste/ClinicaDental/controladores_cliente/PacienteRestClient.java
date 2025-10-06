package es.cheste.ClinicaDental.controladores_cliente;

import es.cheste.ClinicaDental.entidades.Paciente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class PacienteRestClient {
    private final RestTemplate restTemplate;

    @Value("${api.paciente.url}")
    private String apiUrl;

    public PacienteRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Paciente> listarPacientes() {
        Paciente[] pacientesArray = restTemplate.getForObject(apiUrl, Paciente[].class);
        return pacientesArray != null ? Arrays.asList(pacientesArray) : List.of();
    }

    public Paciente obtenerPorId(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, Paciente.class);
    }

    public void agregarPaciente(Paciente paciente) {
        restTemplate.postForObject(apiUrl, paciente, Paciente.class);
    }

    public void actualizarPaciente(Paciente paciente) {
        restTemplate.put(apiUrl + "/" + paciente.getId(), paciente);
    }

    public void eliminarPaciente(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}
