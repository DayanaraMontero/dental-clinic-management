package es.cheste.ClinicaDental.controladores_rest;

import es.cheste.ClinicaDental.entidades.Consulta;
import es.cheste.ClinicaDental.excepcion.DAOException;
import es.cheste.ClinicaDental.servicios.ConsultaServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ConsultaController {
    private final ConsultaServicio salaServicio;

    public ConsultaController(ConsultaServicio salaServicio) {
        this.salaServicio = salaServicio;
    }

    @PostMapping("sala")
    public ResponseEntity<?> create(@RequestBody Consulta sala) {
        Map<String, Object> response = new HashMap<>();
        try {
            Consulta nuevaSala = salaServicio.crearSala(sala);
            response.put("mensaje", "Sala creada con exito");
            return new ResponseEntity<>(nuevaSala, HttpStatus.CREATED);
        } catch (DAOException e) {
            response.put("mensaje", "Error al crear la sala: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("sala/{id}")
    public ResponseEntity<?> update(@RequestBody Consulta sala) {
        Map<String, Object> response = new HashMap<>();
        try {
            Consulta salaActualizada = salaServicio.actualizarSala(sala);
            response.put("mensaje", "Sala actualizada con exito");
            return new ResponseEntity<>(salaActualizada, HttpStatus.OK);
        } catch (DAOException e) {
            response.put("mensaje", "Error al actualizar la sala: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("sala/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            salaServicio.eliminarSala(id);
            response.put("mensaje", "Sala eliminada con exito");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DAOException e) {
            System.out.println("Eliminar Sala: " + e.getMessage());
            response.put("mensaje", "Error al eliminar la sala: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("sala/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Consulta> sala = salaServicio.obtenerSala(id);
            if (sala.isPresent()) {
                response.put("mensaje", "Sala encontrada con ID: " + id);
                return new ResponseEntity<>(sala.get(), HttpStatus.OK);
            } else {
                response.put("mensaje", "Sala no encontrado con ID: " + id);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (DAOException e) {
            response.put("mensaje", "Error al obtener la sala: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("sala")
    public ResponseEntity<?> findAll() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Consulta> salas = salaServicio.listarSalas();
            response.put("mensaje", "Salas encontradas con exito");
            return new ResponseEntity<>(salas, HttpStatus.OK);
        } catch (DAOException e) {
            response.put("mensaje", "Error al obtener las salas: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
