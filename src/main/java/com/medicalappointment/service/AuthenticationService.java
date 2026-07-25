// File: src/com/medicalappointment/service/AuthenticationService.java
package com.medicalappointment.service;

import com.medicalappointment.exception.AuthenticationException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.model.User;

import java.sql.SQLException;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException, SQLException;

    int registerPatient(String firstName, String lastName, String email, String phoneNumber,
                         String password, String confirmPassword)
            throws ValidationException, SQLException;

    void changePassword(int userId, String currentPassword, String newPassword, String confirmPassword)
            throws AuthenticationException, ValidationException, SQLException;
}