package es.cheste.ClinicaDental.controladores_rest;

import es.cheste.ClinicaDental.entidades.Tratamiento;
import es.cheste.ClinicaDental.excepcion.DAOException;
import es.cheste.ClinicaDental.servicios.TratamientoServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TratamientoController {
    private final TratamientoServicio tratamientoServicio;

    public TratamientoController(TratamientoServicio tratamientoServicio) {
        this.tratamientoServicio = tratamientoServicio;
    }

    @PostMapping("tratamiento")
    public ResponseEntity<?> create(@RequestBody Tratamiento tratamiento) {
        Map<String, Object> response = new HashMap<>();
        try {
            Tratamiento nuevoTratamiento = tratamientoServicio.crearTratamiento(tratamiento);
            response.put("mensaje", "Tratamiento creado con exito");
            return new ResponseEntity<>(nuevoTratamiento, HttpStatus.CREATED);
        } catch (DAOException e) {
            response.put("mensaje", "Error al crear el tratamiento: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("tratamiento/{id}")
    public ResponseEntity<?> update(@RequestBody Tratamiento tratamiento) {
        Map<String, Object> response = new HashMap<>();
        try {
            Tratamiento tratamientoActualizado = tratamientoServicio.actualizarTratamiento(tratamiento);
            response.put("mensaje", "Tratamiento actualizado con exito");
            return new ResponseEntity<>(tratamientoActualizado, HttpStatus.OK);
        } catch (DAOException e) {
            response.put("mensaje", "Error al actualizar el tratamiento: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("tratamiento/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            tratamientoServicio.eliminarTratamiento(id);
            response.put("mensaje", "Tratamiento eliminado con exito");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DAOException e) {
            System.out.println("Eliminar Tratamiento: " + e.getMessage());
            response.put("mensaje", "Error al eliminar el tratamiento: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("tratamiento/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Tratamiento> tratamiento = tratamientoServicio.obtenerTratamiento(id);
            if (tratamiento.isPresent()) {
                response.put("mensaje", "Tratamiento encontrado con ID: " + id);
                return new ResponseEntity<>(tratamiento.get(), HttpStatus.OK);
            } else {
                response.put("mensaje", "Tratamiento no encontrado con ID: " + id);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (DAOException e) {
            response.put("mensaje", "Error al obtener el tratamiento: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("tratamiento")
    public ResponseEntity<?> findAll() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Tratamiento> tratamientos = tratamientoServicio.listarTratamientos();
            response.put("mensaje", "Tratamientos encontrados con exito");
            return new ResponseEntity<>(tratamientos, HttpStatus.OK);
        } catch (DAOException e) {
            response.put("mensaje", "Error al obtener los tratamientos: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
