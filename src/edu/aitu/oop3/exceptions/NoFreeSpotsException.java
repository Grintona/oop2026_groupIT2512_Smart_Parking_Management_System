package edu.aitu.oop3.exceptions;

public class NoFreeSpotsException extends RuntimeException {
    public NoFreeSpotsException(String message) {
        super(message);
    }
}