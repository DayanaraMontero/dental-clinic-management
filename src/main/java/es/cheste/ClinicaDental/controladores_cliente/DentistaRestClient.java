package es.cheste.ClinicaDental.controladores_cliente;

import es.cheste.ClinicaDental.entidades.Dentista;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class DentistaRestClient {
    private final RestTemplate restTemplate;

    @Value("${api.dentista.url}")
    private String apiUrl;

    public DentistaRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Dentista> listarDentistas() {
        Dentista[] dentistasArray = restTemplate.getForObject(apiUrl, Dentista[].class);
        return dentistasArray != null ? Arrays.asList(dentistasArray) : List.of();
    }

    public Dentista obtenerPorId(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, Dentista.class);
    }

    public void agregarDentista(Dentista dentista) {
        restTemplate.postForObject(apiUrl, dentista, Dentista.class);
    }

    public void actualizarDentista(Dentista dentista) {
        restTemplate.put(apiUrl + "/" + dentista.getId(), dentista);
    }

    public void eliminarDentista(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}
