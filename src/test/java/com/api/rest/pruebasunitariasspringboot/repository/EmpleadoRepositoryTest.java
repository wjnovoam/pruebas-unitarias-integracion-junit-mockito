package com.api.rest.pruebasunitariasspringboot.repository;

import com.api.rest.pruebasunitariasspringboot.model.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Johan Novoa Melendrez
 * @date 4/10/2022
 */
@DataJpaTest
public class EmpleadoRepositoryTest {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    private Empleado empleado;

    @BeforeEach //Antes de cada método
    void setUp() {
        empleado = Empleado.builder()
                .nombre("William")
                .apellido("Novoa")
                .email("william@gmail.com")
                .build();
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardarEmpleado() {
        //Given - dado o condición previa o configuración de
        Empleado empleado1 = Empleado.builder()
                .nombre("Pepe")
                .apellido("Lopez")
                .email("p12@gmail.com")
                .build();

        //when - acción o el comportamiento que vamos a probar
        Empleado empleadoGuardado = empleadoRepository.save(empleado1);

        //then - verificar la salida
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isPositive();
    }

    @DisplayName("Test para listar a los empleados")
    @Test
    void testListarEmpleados() {
        //Given
        Empleado empleado1 = Empleado.builder()
                .nombre("Julen")
                .apellido("Oliva")
                .email("oliva@gmail.com")
                .build();
        empleadoRepository.save(empleado1);
        empleadoRepository.save(empleado);

        //When
        List<Empleado> listaEmpleados = empleadoRepository.findAll();

        //Then
        assertThat(listaEmpleados).isNotNull();
        assertThat(listaEmpleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener un empleado por id")
    @Test
    void testObtenerEmpleadoPorId() {
        //Given
        empleadoRepository.save(empleado);

        //When
        Empleado empleadoBd = empleadoRepository.findById(empleado.getId()).get();

        //Then
        assertThat(empleadoBd).isNotNull();
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado() {
        //Given
        empleadoRepository.save(empleado);

        //When
        Empleado empleadoGuardado = empleadoRepository.findById(empleado.getId()).get();
        empleadoGuardado.setEmail("eee@gmail.com");
        empleadoGuardado.setNombre("Johan");
        empleadoGuardado.setApellido("Melendrez");

        Empleado empleadoActualizado = empleadoRepository.save(empleadoGuardado);

        //Then
        assertThat(empleadoActualizado.getEmail()).isEqualTo("eee@gmail.com");
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Johan");
        assertThat(empleadoActualizado.getApellido()).isEqualTo("Melendrez");
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado() {
        //Given
        empleadoRepository.save(empleado);

        //When
        empleadoRepository.deleteById(empleado.getId());
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(empleado.getId());

        //Then
        assertThat(empleadoOptional).isEmpty();
    }
}