package com.api.rest.pruebasunitariasspringboot.service.impl;

import com.api.rest.pruebasunitariasspringboot.exception.ResourceNotFoundException;
import com.api.rest.pruebasunitariasspringboot.model.Empleado;
import com.api.rest.pruebasunitariasspringboot.repository.EmpleadoRepository;
import com.api.rest.pruebasunitariasspringboot.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author William Johan Novoa Melendrez
 * @date 4/10/2022
 */
@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        Optional<Empleado> empleadoGuardado = empleadoRepository.findByEmail(empleado.getEmail());
        if(empleadoGuardado.isPresent()){
            throw new ResourceNotFoundException("El empleado con ese email ya existe : " + empleado.getEmail());
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Optional<Empleado> getEmpleadoById(long id) {
        return empleadoRepository.findById(id);
    }

    @Override
    public Empleado updateEmpleado(Empleado empleadoActualizado) {
        return empleadoRepository.save(empleadoActualizado);
    }

    @Override
    public void deleteEmpleado(long id) {
        empleadoRepository.deleteById(id);
    }
}