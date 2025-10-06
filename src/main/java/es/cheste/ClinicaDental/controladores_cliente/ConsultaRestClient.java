package es.cheste.ClinicaDental.controladores_cliente;

import es.cheste.ClinicaDental.entidades.Consulta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ConsultaRestClient {
    private final RestTemplate restTemplate;

    @Value("${api.sala.url}")
    private String apiUrl;

    public ConsultaRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Consulta> listarSalas() {
        Consulta[] salasArray = restTemplate.getForObject(apiUrl, Consulta[].class);
        return salasArray != null ? Arrays.asList(salasArray) : List.of();
    }

    public Consulta obtenerPorId(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, Consulta.class);
    }

    public void agregarSala(Consulta sala) {
        restTemplate.postForObject(apiUrl, sala, Consulta.class);
    }

    public void actualizarSala(Consulta sala) {
        restTemplate.put(apiUrl + "/" + sala.getId(), sala);
    }

    public void eliminarSala(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}
