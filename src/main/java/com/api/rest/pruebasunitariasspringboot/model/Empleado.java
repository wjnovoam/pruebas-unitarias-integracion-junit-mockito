package com.api.rest.pruebasunitariasspringboot.model;

import lombok.*;

import javax.persistence.*;


/**
 * @author William Johan Novoa Melendrez
 * @date 4/10/2022
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre",nullable = false)
    private String nombre;

    @Column(name = "apellido",nullable = false)
    private String apellido;

    @Column(name = "email",nullable = false)
    private String email;
}