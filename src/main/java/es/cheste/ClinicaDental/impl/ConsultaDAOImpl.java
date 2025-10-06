package es.cheste.ClinicaDental.impl;

import es.cheste.ClinicaDental.entidades.Consulta;
import es.cheste.ClinicaDental.interfaces.ConsultaDAO;
import es.cheste.ClinicaDental.repositorios.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ConsultaDAOImpl implements ConsultaDAO {
    private final ConsultaRepository salaRepository;

    @Autowired
    public ConsultaDAOImpl(ConsultaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Transactional(readOnly = true)  // Para indicar que solo es una consulta, no una modificación
    @Override
    public List<Consulta> obtenerTodos() {
        return salaRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Consulta> obtenerPorId(Long id) {
        return salaRepository.findById(id);
    }

    @Transactional
    @Override
    public Consulta insertar(Consulta sala) {
        return salaRepository.save(sala);
    }

    @Transactional
    @Override
    public Consulta actualizar(Consulta sala) {
        return salaRepository.save(sala);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        salaRepository.deleteById(id);
    }
}
