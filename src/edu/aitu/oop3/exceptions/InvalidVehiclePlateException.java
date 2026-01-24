package edu.aitu.oop3.exceptions;

public class InvalidVehiclePlateException extends RuntimeException {
    public InvalidVehiclePlateException(String message) {
        super(message);
    }
}