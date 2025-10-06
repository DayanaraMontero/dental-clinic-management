package es.cheste.ClinicaDental.repositorios;

import es.cheste.ClinicaDental.entidades.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistaRepository extends JpaRepository<Dentista, Long> {

}

