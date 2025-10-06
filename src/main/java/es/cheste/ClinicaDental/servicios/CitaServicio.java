package es.cheste.ClinicaDental.servicios;

import es.cheste.ClinicaDental.entidades.Cita;
import es.cheste.ClinicaDental.excepcion.DAOException;
import es.cheste.ClinicaDental.interfaces.CitaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaServicio {
    private final CitaDAO citaDAO;

    @Autowired
    public CitaServicio(CitaDAO citaDAO) {
        this.citaDAO = citaDAO;
    }

    public Cita crearCita(Cita cita) throws DAOException {
        try {
            return citaDAO.insertar(cita);
        } catch (DAOException e) {
            throw new DAOException("Error al insertar la cita", e);
        }
    }

    public Optional<Cita> obtenerCita(Long id) throws DAOException {
        try {
            return citaDAO.obtenerPorId(id);
        } catch (DAOException e) {
            throw new DAOException("Error al obtener cita con ID: " + id, e);
        }
    }

    public List<Cita> listarCitas() throws DAOException {
        try {
            return citaDAO.obtenerTodos();
        } catch (DAOException e) {
            throw new DAOException("Error al obtener todas las citas", e);
        }
    }

    public Cita actualizarCita(Cita cita) throws DAOException {
        try {
            return citaDAO.actualizar(cita);
        } catch (DAOException e) {
            throw new DAOException("Error al actualizar la cita", e);
        }
    }

    public void eliminarCita(Long id) throws DAOException {
        Optional<Cita> cita = citaDAO.obtenerPorId(id);
        if (cita.isPresent()) {
            try {
                citaDAO.eliminar(id);
            } catch (DAOException e) {
                throw new DAOException("Error al eliminar cita con ID: " + id, e);
            }

        } else {
            throw new DAOException("Cita con ID " + id + " no encontrado", null);
        }
    }
}
