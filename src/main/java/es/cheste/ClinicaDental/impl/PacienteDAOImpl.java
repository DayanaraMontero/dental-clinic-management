package es.cheste.ClinicaDental.impl;

import es.cheste.ClinicaDental.entidades.Paciente;
import es.cheste.ClinicaDental.interfaces.PacienteDAO;
import es.cheste.ClinicaDental.repositorios.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class PacienteDAOImpl implements PacienteDAO {
    private final PacienteRepository pacienteRepository;

    @Autowired
    public PacienteDAOImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional(readOnly = true)  // Para indicar que solo es una consulta, no una modificación
    @Override
    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Paciente> obtenerPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    @Transactional
    @Override
    public Paciente insertar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Transactional
    @Override
    public Paciente actualizar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        pacienteRepository.deleteById(id);
    }
}
