package es.cheste.ClinicaDental.impl;

import es.cheste.ClinicaDental.entidades.Tratamiento;
import es.cheste.ClinicaDental.interfaces.TratamientoDAO;
import es.cheste.ClinicaDental.repositorios.TratamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class TratamientoDAOImpl implements TratamientoDAO {
    private final TratamientoRepository tratamientoRepository;

    @Autowired
    public TratamientoDAOImpl(TratamientoRepository tratamientoRepository) {
        this.tratamientoRepository = tratamientoRepository;
    }

    @Transactional(readOnly = true)  // Para indicar que solo es una consulta, no una modificación
    @Override
    public List<Tratamiento> obtenerTodos() {
        return tratamientoRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Tratamiento> obtenerPorId(Long id) {
        return tratamientoRepository.findById(id);
    }

    @Transactional
    @Override
    public Tratamiento insertar(Tratamiento tratamiento) {
        return tratamientoRepository.save(tratamiento);
    }

    @Transactional
    @Override
    public Tratamiento actualizar(Tratamiento tratamiento) {
        return tratamientoRepository.save(tratamiento);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        tratamientoRepository.deleteById(id);
    }
}
