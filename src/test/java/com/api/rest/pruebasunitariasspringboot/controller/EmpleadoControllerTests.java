package com.api.rest.pruebasunitariasspringboot.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.api.rest.pruebasunitariasspringboot.model.Empleado;
import com.api.rest.pruebasunitariasspringboot.service.EmpleadoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author William Johan Novoa Melendrez
 * @date 5/10/2022
 */
@WebMvcTest
class EmpleadoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean //Agregar objetos simulados al contexto de la aplicación
    private EmpleadoService empleadoService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardarEmpleado() throws Exception {
        //Given
        Empleado empleado = Empleado.builder()
                .id(1L)
                .nombre("Pepe")
                .apellido("Lopez")
                .email("p12@gmail.com")
                .build();
        given(empleadoService.saveEmpleado(any(Empleado.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //When
        ResultActions response = mockMvc.perform(post("/api/empleados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empleado)));

        //Then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(empleado.getNombre())))
                .andExpect(jsonPath("$.apellido", is(empleado.getApellido())))
                .andExpect(jsonPath("$.email", is(empleado.getEmail())));
    }

    @DisplayName("Test para listar empleados")
    @Test
    void testListarEmpleados() throws Exception {
        //given
        List<Empleado> listaEmpleados = new ArrayList<>();
        listaEmpleados.add(Empleado.builder().nombre("Christian").apellido("Ramirez").email("c1@gmail.com").build());
        listaEmpleados.add(Empleado.builder().nombre("Gabriel").apellido("Ramirez").email("g1@gmail.com").build());
        listaEmpleados.add(Empleado.builder().nombre("Julen").apellido("Ramirez").email("cj@gmail.com").build());
        listaEmpleados.add(Empleado.builder().nombre("Biaggio").apellido("Ramirez").email("b1@gmail.com").build());
        listaEmpleados.add(Empleado.builder().nombre("Adrian").apellido("Ramirez").email("a@gmail.com").build());
        given(empleadoService.getAllEmpleados()).willReturn(listaEmpleados);

        //When
        ResultActions response = mockMvc.perform(get("/api/empleados"));

        //Then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listaEmpleados.size())));

    }

    @DisplayName("Test obtener empleado por ID")
    @Test
    void testObtenerEmpleadoPorId() throws Exception {
        //given
        long empleadoId = 1L;
        Empleado empleado = Empleado.builder()
                .nombre("Christian")
                .apellido("Ramirez")
                .email("c1@gmail.com")
                .build();
        given(empleadoService.getEmpleadoById(empleadoId)).willReturn(Optional.of(empleado));

        //when
        ResultActions response = mockMvc.perform(get("/api/empleados/{id}",empleadoId));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre",is(empleado.getNombre())))
                .andExpect(jsonPath("$.apellido",is(empleado.getApellido())))
                .andExpect(jsonPath("$.email",is(empleado.getEmail())));
    }

    @DisplayName("Test obtener empleado no encontrado")
    @Test
    void testObtenerEmpleadoNoEncontrado() throws Exception {
        //given
        long empleadoId = 1L;
        given(empleadoService.getEmpleadoById(empleadoId)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(get("/api/empleados/{id}",empleadoId));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado() throws Exception {
        //given
        long empleadoId = 1L;
        Empleado empleadoGuardado = Empleado.builder()
                .nombre("Christian")
                .apellido("Lopez")
                .email("c1@gmail.com")
                .build();

        Empleado empleadoActualizado = Empleado.builder()
                .nombre("Christian Raul")
                .apellido("Ramirez")
                .email("c231@gmail.com")
                .build();

        given(empleadoService.getEmpleadoById(empleadoId)).willReturn(Optional.of(empleadoGuardado));
        given(empleadoService.updateEmpleado(any(Empleado.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/api/empleados/{id}",empleadoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empleadoActualizado)));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre",is(empleadoActualizado.getNombre())))
                .andExpect(jsonPath("$.apellido",is(empleadoActualizado.getApellido())))
                .andExpect(jsonPath("$.email",is(empleadoActualizado.getEmail())));
    }

    @DisplayName("Test para actualizar empleado no encontrado")
    @Test
    void testActualizarEmpleadoNoEncontrado() throws Exception {
        //given
        long empleadoId = 1L;
        Empleado empleadoGuardado = Empleado.builder()
                .nombre("Christian")
                .apellido("Lopez")
                .email("c1@gmail.com")
                .build();

        Empleado empleadoActualizado = Empleado.builder()
                .nombre("Christian Raul")
                .apellido("Ramirez")
                .email("c231@gmail.com")
                .build();

        given(empleadoService.getEmpleadoById(empleadoId)).willReturn(Optional.empty());
        given(empleadoService.updateEmpleado(any(Empleado.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/api/empleados/{id}",empleadoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empleadoActualizado)));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado() throws Exception {
        //given
        long empleadoId = 1L;
        willDoNothing().given(empleadoService).deleteEmpleado(empleadoId);

        //when
        ResultActions response = mockMvc.perform(delete("/api/empleados/{id}",empleadoId));

        //then
        response.andExpect(status().isOk())
                .andDo(print());
    }
}