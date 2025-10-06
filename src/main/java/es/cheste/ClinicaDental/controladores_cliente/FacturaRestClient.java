package es.cheste.ClinicaDental.controladores_cliente;

import es.cheste.ClinicaDental.entidades.Factura;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class FacturaRestClient {
    private final RestTemplate restTemplate;

    @Value("${api.factura.url}")
    private String apiUrl;

    public FacturaRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Factura> listarFacturas() {
        Factura[] facturasArray = restTemplate.getForObject(apiUrl, Factura[].class);
        return facturasArray != null ? Arrays.asList(facturasArray) : List.of();
    }

    public Factura obtenerPorId(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, Factura.class);
    }

    public void agregarFactura(Factura factura) {
        restTemplate.postForObject(apiUrl, factura, Factura.class);
    }

    public void actualizarFactura(Factura factura) {
        restTemplate.put(apiUrl + "/" + factura.getId(), factura);
    }

    public void eliminarFactura(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}
