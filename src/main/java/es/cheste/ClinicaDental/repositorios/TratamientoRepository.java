package es.cheste.ClinicaDental.repositorios;

import es.cheste.ClinicaDental.entidades.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {

}
