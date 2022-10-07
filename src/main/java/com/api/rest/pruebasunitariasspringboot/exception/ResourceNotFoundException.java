package com.api.rest.pruebasunitariasspringboot.exception;

/**
 * @author William Johan Novoa Melendrez
 * @date 4/10/2022
 */
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}