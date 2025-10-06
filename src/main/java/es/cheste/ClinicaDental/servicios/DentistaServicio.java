package es.cheste.ClinicaDental.servicios;

import es.cheste.ClinicaDental.entidades.Dentista;
import es.cheste.ClinicaDental.excepcion.DAOException;
import es.cheste.ClinicaDental.interfaces.DentistaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistaServicio {
    private final DentistaDAO dentistaDAO;

    @Autowired
    public DentistaServicio(DentistaDAO dentistaDAO) {
        this.dentistaDAO = dentistaDAO;
    }

    public Dentista crearDentista(Dentista dentista) throws DAOException {
        try {
            return dentistaDAO.insertar(dentista);
        } catch (DAOException e) {
            throw new DAOException("Error al insertar al dentista", e);
        }
    }

    public Optional<Dentista> obtenerDentista(Long id) throws DAOException {
        try {
            return dentistaDAO.obtenerPorId(id);
        } catch (DAOException e) {
            throw new DAOException("Error al obtener dentista con ID: " + id, e);
        }
    }

    public List<Dentista> listarDentistas() throws DAOException {
        try {
            return dentistaDAO.obtenerTodos();
        } catch (DAOException e) {
            throw new DAOException("Error al obtener todos los dentistas", e);
        }
    }

    public Dentista actualizarDentista(Dentista dentista) throws DAOException {
        try {
            return dentistaDAO.actualizar(dentista);
        } catch (DAOException e) {
            throw new DAOException("Error al actualizar el dentista", e);
        }
    }

    public void eliminarDentista(Long id) throws DAOException {
        Optional<Dentista> dentista = dentistaDAO.obtenerPorId(id);
        if (dentista.isPresent()) {
            try {
                dentistaDAO.eliminar(id);
            } catch (DAOException e) {
                throw new DAOException("Error al eliminar dentista con ID: " + id, e);
            }

        } else {
            throw new DAOException("Dentista con ID " + id + " no encontrado", null);
        }
    }
}

