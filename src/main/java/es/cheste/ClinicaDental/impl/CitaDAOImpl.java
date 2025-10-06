package es.cheste.ClinicaDental.impl;

import es.cheste.ClinicaDental.entidades.Cita;
import es.cheste.ClinicaDental.interfaces.CitaDAO;
import es.cheste.ClinicaDental.repositorios.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class CitaDAOImpl implements CitaDAO {
    private final CitaRepository citaRepository;

    @Autowired
    public CitaDAOImpl(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Transactional(readOnly = true)  // Para indicar que solo es una consulta, no una modificación
    @Override
    public List<Cita> obtenerTodos() {
        return citaRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Cita> obtenerPorId(Long id) {
        return citaRepository.findById(id);
    }

    @Transactional
    @Override
    public Cita insertar(Cita cita) {
        return citaRepository.save(cita);
    }

    @Transactional
    @Override
    public Cita actualizar(Cita cita) {
        return citaRepository.save(cita);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }
}
