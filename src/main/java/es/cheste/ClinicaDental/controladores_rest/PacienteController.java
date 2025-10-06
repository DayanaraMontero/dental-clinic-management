package es.cheste.ClinicaDental.controladores_rest;

import es.cheste.ClinicaDental.entidades.Paciente;
import es.cheste.ClinicaDental.excepcion.DAOException;
import es.cheste.ClinicaDental.servicios.PacienteServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PacienteController {
    private final PacienteServicio pacienteServicio;

    public PacienteController(PacienteServicio pacienteServicio) {
        this.pacienteServicio = pacienteServicio;
    }

    @PostMapping("paciente")
    public ResponseEntity<?> create(@RequestBody Paciente paciente) {
        Map<String, Object> response = new HashMap<>();
        try {
            Paciente nuevoPaciente = pacienteServicio.crearPaciente(paciente);
            response.put("mensaje", "Paciente creado con exito");
            return new ResponseEntity<>(nuevoPaciente, HttpStatus.CREATED);
        } catch (DAOException e) {
            response.put("mensaje", "Error al crear el paciente: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("paciente/{id}")
    public ResponseEntity<?> update(@RequestBody Paciente paciente) {
        Map<String, Object> response = new HashMap<>();
        try {
            Paciente pacienteActualizado = pacienteServicio.actualizarPaciente(paciente);
            response.put("mensaje", "Paciente actualizado con exito");
            return new ResponseEntity<>(pacienteActualizado, HttpStatus.OK);
        } catch (DAOException e) {
            response.put("mensaje", "Error al actualizar el paciente: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("paciente/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            pacienteServicio.eliminarPaciente(id);
            response.put("mensaje", "Paciente eliminado con exito");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DAOException e) {
            System.out.println("Eliminar Paciente: " + e.getMessage());
            response.put("mensaje", "Error al eliminar el paciente: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("paciente/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Paciente> paciente = pacienteServicio.obtenerPaciente(id);
            if (paciente.isPresent()) {
                response.put("mensaje", "Paciente encontrado con ID: " + id);
                return new ResponseEntity<>(paciente.get(), HttpStatus.OK);
            } else {
                response.put("mensaje", "Paciente no encontrado con ID: " + id);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (DAOException e) {
            response.put("mensaje", "Error al obtener el paciente: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("paciente")
    public ResponseEntity<?> findAll() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Paciente> pacientes = pacienteServicio.listarPacientes();
            response.put("mensaje", "Pacientes encontrados con exito");
            return new ResponseEntity<>(pacientes, HttpStatus.OK);
        } catch (DAOException e) {
            response.put("mensaje", "Error al obtener los pacientes: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
