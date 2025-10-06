package es.cheste.ClinicaDental.servicios;

import es.cheste.ClinicaDental.entidades.Paciente;
import es.cheste.ClinicaDental.excepcion.DAOException;
import es.cheste.ClinicaDental.interfaces.PacienteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServicio {
    private final PacienteDAO pacienteDAO;

    @Autowired
    public PacienteServicio(PacienteDAO pacienteDAO) {
        this.pacienteDAO = pacienteDAO;
    }

    public Paciente crearPaciente(Paciente paciente) throws DAOException {
        try {
            return pacienteDAO.insertar(paciente);
        } catch (DAOException e) {
            throw new DAOException("Error al insertar el paciente", e);
        }
    }

    public Optional<Paciente> obtenerPaciente(Long id) throws DAOException {
        try {
            return pacienteDAO.obtenerPorId(id);
        } catch (DAOException e) {
            throw new DAOException("Error al obtener paciente con ID: " + id, e);
        }
    }

    public List<Paciente> listarPacientes() throws DAOException {
        try {
            return pacienteDAO.obtenerTodos();
        } catch (DAOException e) {
            throw new DAOException("Error al obtener todos los pacientes", e);
        }
    }

    public Paciente actualizarPaciente(Paciente paciente) throws DAOException {
        try {
            return pacienteDAO.actualizar(paciente);
        } catch (DAOException e) {
            throw new DAOException("Error al actualizar el paciente", e);
        }
    }

    public void eliminarPaciente(Long id) throws DAOException {
        Optional<Paciente> paciente = pacienteDAO.obtenerPorId(id);
        if (paciente.isPresent()) {
            try {
                pacienteDAO.eliminar(id);
            } catch (DAOException e) {
                throw new DAOException("Error al eliminar paciente con ID: " + id, e);
            }

        } else {
            throw new DAOException("Paciente con ID " + id + " no encontrado", null);
        }
    }
}
