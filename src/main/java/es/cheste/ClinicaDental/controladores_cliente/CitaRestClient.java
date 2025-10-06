package es.cheste.ClinicaDental.controladores_cliente;

import es.cheste.ClinicaDental.entidades.Cita;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class CitaRestClient {
    private final RestTemplate restTemplate;

    @Value("${api.cita.url}")
    private String apiUrl;

    public CitaRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Cita> listarCitas() {
        Cita[] citasArray = restTemplate.getForObject(apiUrl, Cita[].class);
        return citasArray != null ? Arrays.asList(citasArray) : List.of();
    }

    public Cita obtenerPorId(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, Cita.class);
    }

    public void agregarCita(Cita cita) {
        restTemplate.postForObject(apiUrl, cita, Cita.class);
    }

    public void actualizarCita(Cita cita) {
        restTemplate.put(apiUrl + "/" + cita.getId(), cita);
    }

    public void eliminarCita(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}
