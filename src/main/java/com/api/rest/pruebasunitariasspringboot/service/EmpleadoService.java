package com.api.rest.pruebasunitariasspringboot.service;

import com.api.rest.pruebasunitariasspringboot.model.Empleado;

import java.util.List;
import java.util.Optional;

/**
 * @author William Johan Novoa Melendrez
 * @date 4/10/2022
 */
public interface EmpleadoService {
    Empleado saveEmpleado(Empleado empleado);

    List<Empleado> getAllEmpleados();

    Optional<Empleado> getEmpleadoById(long id);

    Empleado updateEmpleado(Empleado empleadoActualizado);

    void deleteEmpleado(long id);
}