package es.cheste.ClinicaDental.controladores_rest;

import es.cheste.ClinicaDental.entidades.Cita;
import es.cheste.ClinicaDental.excepcion.DAOException;
import es.cheste.ClinicaDental.servicios.CitaServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CitaController {
    private final CitaServicio citaServicio;

    public CitaController(CitaServicio citaServicio) {
        this.citaServicio = citaServicio;
    }

    @PostMapping("cita")
    public ResponseEntity<?> create(@RequestBody Cita cita) {
        Map<String, Object> response = new HashMap<>();
        try {
            Cita nuevaCita = citaServicio.crearCita(cita);
            response.put("mensaje", "Cita creada con exito");
            return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
        } catch (DAOException e) {
            response.put("mensaje", "Error al crear la cita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("cita/{id}")
    public ResponseEntity<?> update(@RequestBody Cita cita) {
        Map<String, Object> response = new HashMap<>();
        try {
            Cita citaActualizada = citaServicio.actualizarCita(cita);
            response.put("mensaje", "Cita actualizada con exito");
            return new ResponseEntity<>(citaActualizada, HttpStatus.OK);
        } catch (DAOException e) {
            response.put("mensaje", "Error al actualizar la cita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("cita/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            citaServicio.eliminarCita(id);
            response.put("mensaje", "Cita eliminada con exito");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DAOException e) {
            System.out.println("Eliminar Cita: " + e.getMessage());
            response.put("mensaje", "Error al eliminar la cita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("cita/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Cita> cita = citaServicio.obtenerCita(id);
            if (cita.isPresent()) {
                response.put("mensaje", "Cita encontrada con ID: " + id);
                return new ResponseEntity<>(cita.get(), HttpStatus.OK);
            } else {
                response.put("mensaje", "Cita no encontrada con ID: " + id);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (DAOException e) {
            response.put("mensaje", "Error al obtener la cita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("cita")
    public ResponseEntity<?> findAll() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Cita> citas = citaServicio.listarCitas();
            response.put("mensaje", "Citas encontradas con exito");
            return new ResponseEntity<>(citas, HttpStatus.OK);
        } catch (DAOException e) {
            response.put("mensaje", "Error al obtener las citas: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
