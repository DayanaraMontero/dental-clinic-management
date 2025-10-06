package es.cheste.ClinicaDental.impl;

import es.cheste.ClinicaDental.entidades.Dentista;
import es.cheste.ClinicaDental.interfaces.DentistaDAO;
import es.cheste.ClinicaDental.repositorios.DentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class DentistaDAOImpl implements DentistaDAO {
    private final DentistaRepository dentistaRepository;

    @Autowired
    public DentistaDAOImpl(DentistaRepository dentistaRepository) {
        this.dentistaRepository = dentistaRepository;
    }

    @Transactional(readOnly = true)  // Para indicar que solo es una consulta, no una modificación
    @Override
    public List<Dentista> obtenerTodos() {
        return dentistaRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Dentista> obtenerPorId(Long id) {
        return dentistaRepository.findById(id);
    }

    @Transactional
    @Override
    public Dentista insertar(Dentista dentista) {
        return dentistaRepository.save(dentista);
    }

    @Transactional
    @Override
    public Dentista actualizar(Dentista dentista) {
        return dentistaRepository.save(dentista);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        dentistaRepository.deleteById(id);
    }
}

