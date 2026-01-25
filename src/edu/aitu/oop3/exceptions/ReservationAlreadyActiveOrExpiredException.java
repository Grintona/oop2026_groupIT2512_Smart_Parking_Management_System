package edu.aitu.oop3.exceptions;

public class ReservationAlreadyActiveOrExpiredException extends RuntimeException {
    public ReservationAlreadyActiveOrExpiredException(String message) {
        super(message);
    }
}