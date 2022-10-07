package com.api.rest.pruebasunitariasspringboot.controller;

import com.api.rest.pruebasunitariasspringboot.model.Empleado;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author William Johan Novoa Melendrez
 * @date 6/10/2022
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 class EmpleadoControllerTestRestTemplateTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @DisplayName("Test de integración para guardar un empleado")
    @Test
    @Order(1)
    void testGuardarEmpleado(){
        Empleado empleado = Empleado.builder()
                .id(1L)
                .nombre("Christian")
                .apellido("Ramirez")
                .email("c1@gmail.com")
                .build();
        ResponseEntity<Empleado> respuesta = testRestTemplate.postForEntity("http://localhost:8090/api/empleados",
                empleado,Empleado.class);
        assertEquals(HttpStatus.CREATED,respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON,respuesta.getHeaders().getContentType());

        Empleado empleadoCreado = respuesta.getBody();
        assertNotNull(empleadoCreado);

        assertEquals(1L,empleadoCreado.getId());
        assertEquals("Christian",empleadoCreado.getNombre());
        assertEquals("Ramirez",empleado.getApellido());
        assertEquals("c1@gmail.com",empleadoCreado.getEmail());
    }

    @DisplayName("Test de integración para listar todos los empelados")
    @Test
    @Order(2)
    void testListarEmpleados(){
        ResponseEntity<Empleado[]> respuesta = testRestTemplate.getForEntity("http://localhost:8090/api/empleados",
                Empleado[].class);
        List<Empleado> empleados = Arrays.asList(Objects.requireNonNull(respuesta.getBody()));

        assertEquals(HttpStatus.OK,respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON,respuesta.getHeaders().getContentType());

        assertEquals(1,empleados.size());
        assertEquals(1L,empleados.get(0).getId());
        assertEquals("Christian",empleados.get(0).getNombre());
        assertEquals("Ramirez",empleados.get(0).getApellido());
        assertEquals("c1@gmail.com",empleados.get(0).getEmail());
    }

    @DisplayName("Test de integración para obtener un empleado")
    @Test
    @Order(3)
    void testObtenerEmpleado(){
        ResponseEntity<Empleado> respuesta = testRestTemplate.getForEntity("http://localhost:8090/api/empleados/1",
                Empleado.class);
        Empleado empleado = respuesta.getBody();

        assertEquals(HttpStatus.OK,respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON,respuesta.getHeaders().getContentType());

        assertNotNull(empleado);
        assertEquals(1L,empleado.getId());
        assertEquals("Christian",empleado.getNombre());
        assertEquals("Ramirez",empleado.getApellido());
        assertEquals("c1@gmail.com",empleado.getEmail());
    }

    @DisplayName("Test de integración para eliminar un empleado")
    @Test
    @Order(4)
    void testEliminarEmpleado(){
        ResponseEntity<Empleado[]> respuesta = testRestTemplate.getForEntity("http://localhost:8090/api/empleados",
                Empleado[].class);
        List<Empleado> empleados = Arrays.asList(Objects.requireNonNull(respuesta.getBody()));
        assertEquals(1,empleados.size());

        Map<String,Long> pathVariables = new HashMap<>();
        pathVariables.put("id",1L);
        ResponseEntity<Void> exchange = testRestTemplate.exchange("http://localhost:8090/api/empleados/{id}",
                HttpMethod.DELETE,null,Void.class,pathVariables);

        assertEquals(HttpStatus.OK,exchange.getStatusCode());
        assertFalse(exchange.hasBody());

        respuesta = testRestTemplate.getForEntity("http://localhost:8090/api/empleados",Empleado[].class);
        empleados = Arrays.asList(Objects.requireNonNull(respuesta.getBody()));
        assertEquals(0,empleados.size());

        ResponseEntity<Empleado> respuestaDetalle = testRestTemplate.getForEntity("http://localhost:8090/api" +
                "/empleados/2",Empleado.class);
        assertEquals(HttpStatus.NOT_FOUND,respuestaDetalle.getStatusCode());
        assertFalse(respuestaDetalle.hasBody());
    }
}