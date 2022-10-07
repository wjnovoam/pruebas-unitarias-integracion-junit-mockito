package com.api.rest.pruebasunitariasspringboot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import com.api.rest.pruebasunitariasspringboot.exception.ResourceNotFoundException;
import com.api.rest.pruebasunitariasspringboot.model.Empleado;
import com.api.rest.pruebasunitariasspringboot.repository.EmpleadoRepository;
import com.api.rest.pruebasunitariasspringboot.service.impl.EmpleadoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

/**
 * @author William Johan Novoa Melendrez
 * @date 4/10/2022
 */
@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTests {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoServiceImpl empleadoService;

    private Empleado empleado;

    @BeforeEach
        //Antes de cada método
    void setUp() {
        empleado = Empleado.builder()
                .id(1L)
                .nombre("William")
                .apellido("Novoa")
                .email("william@gmail.com")
                .build();
    }

    @DisplayName("Test para guardar a un empleado")
    @Test
    void testGuardarEmpleado() {
        //Given
        given(empleadoRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.empty());

        given(empleadoRepository.save(empleado))
                .willReturn(empleado);

        //When
        Empleado empleadoGuardado = empleadoService.saveEmpleado(empleado);

        //Then
        assertThat(empleadoGuardado).isNotNull();

    }

    @DisplayName("Test para guardar a un empleado con ThrowException")
    @Test
    void testGuardarEmpleadoConThrowException() {
        //Given
        given(empleadoRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.of(empleado));

        //When
        assertThrows(ResourceNotFoundException.class, ()-> {
            empleadoService.saveEmpleado(empleado);
        });

        //Then
        verify(empleadoRepository,never()).save(any(Empleado.class));
    }

    @DisplayName("Test para listar a los empleados")
    @Test
    void testListarEmpleados() {
        //given
        Empleado empleado1 = Empleado.builder()
                .id(2L)
                .nombre("Julen")
                .apellido("Oliva")
                .email("Will@gmail.com")
                .build();
        given(empleadoRepository.findAll()).willReturn(List.of(empleado, empleado1));

        //When
        List<Empleado> empleados = empleadoService.getAllEmpleados();

        //Then
        assertThat(empleados).hasSize(2).isNotNull();
    }

    @DisplayName("Test para retornar una lista vacia")
    @Test
    void testListarColeccionEmpleadosVacia() {
        //Given
        given(empleadoRepository.findAll()).willReturn(Collections.emptyList());

        //When
        List<Empleado> listaEmpleados = empleadoService.getAllEmpleados();

        //Then
        assertThat(listaEmpleados).isEmpty();
    }

    @DisplayName("Test para obtener un empleado por ID")
    @Test
    void testObtenerEmpleadoPorId() {
        //Given
        given(empleadoRepository.findById(1L)).willReturn(Optional.of(empleado));

        //When
        Empleado empleadoGuardado = empleadoService.getEmpleadoById(empleado.getId()).get();

        //Then
        assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado() {
        //Given
        given(empleadoRepository.save(empleado)).willReturn(empleado);
        empleado.setEmail("chra@gmail.com");
        empleado.setNombre("Johann");

        //When
        Empleado empleadoActualizado = empleadoService.updateEmpleado(empleado);

        //Then
        assertThat(empleadoActualizado.getEmail()).isEqualTo("chra@gmail.com");
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Johann");
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado() {
        //Given
        Long empleadoId = 1L;
        willDoNothing().given(empleadoRepository)
                .deleteById(empleadoId);

        //When
        empleadoService.deleteEmpleado(empleadoId);

        //Then
        verify(empleadoRepository, times(1)).deleteById(empleadoId);
    }
}