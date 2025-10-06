package es.cheste.ClinicaDental.servicios;

import es.cheste.ClinicaDental.entidades.Tratamiento;
import es.cheste.ClinicaDental.excepcion.DAOException;
import es.cheste.ClinicaDental.interfaces.TratamientoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TratamientoServicio {
    private final TratamientoDAO tratamientoDAO;

    @Autowired
    public TratamientoServicio(TratamientoDAO tratamientoDAO) {
        this.tratamientoDAO = tratamientoDAO;
    }

    public Tratamiento crearTratamiento(Tratamiento tratamiento) throws DAOException {
        try {
            return tratamientoDAO.insertar(tratamiento);
        } catch (DAOException e) {
            throw new DAOException("Error al insertar el tratamiento", e);
        }
    }

    public Optional<Tratamiento> obtenerTratamiento(Long id) throws DAOException {
        try {
            return tratamientoDAO.obtenerPorId(id);
        } catch (DAOException e) {
            throw new DAOException("Error al obtener tratamiento con ID: " + id, e);
        }
    }

    public List<Tratamiento> listarTratamientos() throws DAOException {
        try {
            return tratamientoDAO.obtenerTodos();
        } catch (DAOException e) {
            throw new DAOException("Error al obtener todos los tratamientos", e);
        }
    }

    public Tratamiento actualizarTratamiento(Tratamiento tratamiento) throws DAOException {
        try {
            return tratamientoDAO.actualizar(tratamiento);
        } catch (DAOException e) {
            throw new DAOException("Error al actualizar el tratamiento", e);
        }
    }

    public void eliminarTratamiento(Long id) throws DAOException {
        Optional<Tratamiento> tratamiento = tratamientoDAO.obtenerPorId(id);
        if (tratamiento.isPresent()) {
            try {
                tratamientoDAO.eliminar(id);
            } catch (DAOException e) {
                throw new DAOException("Error al eliminar tratamiento con ID: " + id, e);
            }

        } else {
            throw new DAOException("Tratamiento con ID " + id + " no encontrado", null);
        }
    }
}
