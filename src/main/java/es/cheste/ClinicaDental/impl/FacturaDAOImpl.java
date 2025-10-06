package es.cheste.ClinicaDental.impl;

import es.cheste.ClinicaDental.entidades.Factura;
import es.cheste.ClinicaDental.interfaces.FacturaDAO;
import es.cheste.ClinicaDental.repositorios.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class FacturaDAOImpl implements FacturaDAO {
    private final FacturaRepository facturaRepository;

    @Autowired
    public FacturaDAOImpl(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    @Transactional(readOnly = true)  // Para indicar que solo es una consulta, no una modificación
    @Override
    public List<Factura> obtenerTodos() {
        return facturaRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Factura> obtenerPorId(Long id) {
        return facturaRepository.findById(id);
    }

    @Transactional
    @Override
    public Factura insertar(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Transactional
    @Override
    public Factura actualizar(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        facturaRepository.deleteById(id);
    }
}
