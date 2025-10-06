package es.cheste.ClinicaDental.controladores_rest;

import es.cheste.ClinicaDental.entidades.Dentista;
import es.cheste.ClinicaDental.excepcion.DAOException;
import es.cheste.ClinicaDental.servicios.DentistaServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class DentistaController {
    private final DentistaServicio dentistaServicio;

    public DentistaController(DentistaServicio dentistaServicio) {
        this.dentistaServicio = dentistaServicio;
    }

    @PostMapping("dentista")
    public ResponseEntity<?> create(@RequestBody Dentista dentista) {
        Map<String, Object> response = new HashMap<>();
        try {
            Dentista nuevoDentista = dentistaServicio.crearDentista(dentista);
            response.put("mensaje", "Dentista creado con exito");
            return new ResponseEntity<>(nuevoDentista, HttpStatus.CREATED);
        } catch (DAOException e) {
            response.put("mensaje", "Error al crear el dentista: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("dentista/{id}")
    public ResponseEntity<?> update(@RequestBody Dentista dentista) {
        Map<String, Object> response = new HashMap<>();
        try {
            Dentista dentistaActualizado = dentistaServicio.actualizarDentista(dentista);
            response.put("mensaje", "Dentista actualizado con exito");
            return new ResponseEntity<>(dentistaActualizado, HttpStatus.OK);
        } catch (DAOException e) {
            response.put("mensaje", "Error al actualizar el dentista: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("dentista/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            dentistaServicio.eliminarDentista(id);
            response.put("mensaje", "Dentista eliminado con exito");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DAOException e) {
            System.out.println("Eliminar Dentista: " + e.getMessage());
            response.put("mensaje", "Error al eliminar el dentista: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("dentista/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Dentista> dentista = dentistaServicio.obtenerDentista(id);
            if (dentista.isPresent()) {
                response.put("mensaje", "Dentista encontrado con ID: " + id);
                return new ResponseEntity<>(dentista.get(), HttpStatus.OK);
            } else {
                response.put("mensaje", "Dentista no encontrado con ID: " + id);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (DAOException e) {
            response.put("mensaje", "Error al obtener el dentista: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("dentista")
    public ResponseEntity<?> findAll() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Dentista> dentistas = dentistaServicio.listarDentistas();
            response.put("mensaje", "Dentistas encontrados con exito");
            return new ResponseEntity<>(dentistas, HttpStatus.OK);
        } catch (DAOException e) {
            response.put("mensaje", "Error al obtener los dentistas: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

