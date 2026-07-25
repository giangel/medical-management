// File: src/com/medicalappointment/exception/BookingConflictException.java
package com.medicalappointment.exception;

public class BookingConflictException extends Exception {
    public BookingConflictException(String message) {
        super(message);
    }
}