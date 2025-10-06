package es.cheste.ClinicaDental.controladores_rest;

import es.cheste.ClinicaDental.entidades.Factura;
import es.cheste.ClinicaDental.excepcion.DAOException;
import es.cheste.ClinicaDental.servicios.FacturaServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class FacturaController {
    private final FacturaServicio facturaServicio;

    public FacturaController(FacturaServicio facturaServicio) {
        this.facturaServicio = facturaServicio;
    }

    @PostMapping("factura")
    public ResponseEntity<?> create(@RequestBody Factura factura) {
        Map<String, Object> response = new HashMap<>();
        try {
            Factura nuevaFactura = facturaServicio.crearFactura(factura);
            response.put("mensaje", "Factura creada con exito");
            return new ResponseEntity<>(nuevaFactura, HttpStatus.CREATED);
        } catch (DAOException e) {
            response.put("mensaje", "Error al crear la factura: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("factura/{id}")
    public ResponseEntity<?> update(@RequestBody Factura factura) {
        Map<String, Object> response = new HashMap<>();
        try {
            Factura facturaActualizada = facturaServicio.actualizarFactura(factura);
            response.put("mensaje", "Factura actualizada con exito");
            return new ResponseEntity<>(facturaActualizada, HttpStatus.OK);
        } catch (DAOException e) {
            response.put("mensaje", "Error al actualizar la factura: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("factura/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            facturaServicio.eliminarFactura(id);
            response.put("mensaje", "Factura eliminada con exito");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DAOException e) {
            System.out.println("Eliminar Factura: " + e.getMessage());
            response.put("mensaje", "Error al eliminar la factura: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("factura/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Factura> factura = facturaServicio.obtenerFactura(id);
            if (factura.isPresent()) {
                response.put("mensaje", "Factura encontrada con ID: " + id);
                return new ResponseEntity<>(factura.get(), HttpStatus.OK);
            } else {
                response.put("mensaje", "Factura no encontrada con ID: " + id);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (DAOException e) {
            response.put("mensaje", "Error al obtener la factura: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("factura")
    public ResponseEntity<?> findAll() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Factura> facturas = facturaServicio.listarFacturas();
            response.put("mensaje", "Facturas encontradas con exito");
            return new ResponseEntity<>(facturas, HttpStatus.OK);
        } catch (DAOException e) {
            response.put("mensaje", "Error al obtener las facturas: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
