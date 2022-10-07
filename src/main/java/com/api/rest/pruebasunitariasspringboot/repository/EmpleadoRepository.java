package com.api.rest.pruebasunitariasspringboot.repository;

import com.api.rest.pruebasunitariasspringboot.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author William Johan Novoa Melendrez
 * @date 4/10/2022
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByEmail(String email);
}