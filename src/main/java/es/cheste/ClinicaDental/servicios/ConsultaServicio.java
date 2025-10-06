package es.cheste.ClinicaDental.servicios;

import es.cheste.ClinicaDental.entidades.Consulta;
import es.cheste.ClinicaDental.excepcion.DAOException;
import es.cheste.ClinicaDental.interfaces.ConsultaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaServicio {
    private final ConsultaDAO salaDAO;

    @Autowired
    public ConsultaServicio(ConsultaDAO salaDAO) {
        this.salaDAO = salaDAO;
    }

    public Consulta crearSala(Consulta sala) throws DAOException {
        try {
            return salaDAO.insertar(sala);
        } catch (DAOException e) {
            throw new DAOException("Error al insertar la sala", e);
        }
    }

    public Optional<Consulta> obtenerSala(Long id) throws DAOException {
        try {
            return salaDAO.obtenerPorId(id);
        } catch (DAOException e) {
            throw new DAOException("Error al obtener sala con ID: " + id, e);
        }
    }

    public List<Consulta> listarSalas() throws DAOException {
        try {
            return salaDAO.obtenerTodos();
        } catch (DAOException e) {
            throw new DAOException("Error al obtener todas las salas", e);
        }
    }

    public Consulta actualizarSala(Consulta sala) throws DAOException {
        try {
            return salaDAO.actualizar(sala);
        } catch (DAOException e) {
            throw new DAOException("Error al actualizar la sala", e);
        }
    }

    public void eliminarSala(Long id) throws DAOException {
        Optional<Consulta> sala = salaDAO.obtenerPorId(id);
        if (sala.isPresent()) {
            try {
                salaDAO.eliminar(id);
            } catch (DAOException e) {
                throw new DAOException("Error al eliminar sala con ID: " + id, e);
            }

        } else {
            throw new DAOException("Sala con ID " + id + " no encontrado", null);
        }
    }
}
