package es.cheste.ClinicaDental.controladores_cliente;

import es.cheste.ClinicaDental.entidades.Tratamiento;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class TratamientoRestClient {
    private final RestTemplate restTemplate;

    @Value("${api.tratamiento.url}")
    private String apiUrl;

    public TratamientoRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Tratamiento> listarTratamientos() {
        Tratamiento[] tratamientosArray = restTemplate.getForObject(apiUrl, Tratamiento[].class);
        return tratamientosArray != null ? Arrays.asList(tratamientosArray) : List.of();
    }

    public Tratamiento obtenerPorId(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, Tratamiento.class);
    }

    public void agregarTratamiento(Tratamiento tratamiento) {
        restTemplate.postForObject(apiUrl, tratamiento, Tratamiento.class);
    }

    public void actualizarTratamiento(Tratamiento tratamiento) {
        restTemplate.put(apiUrl + "/" + tratamiento.getId(), tratamiento);
    }

    public void eliminarTratamiento(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}
