// File: src/com/medicalappointment/service/impl/AuthenticationServiceImpl.java
package com.medicalappointment.service.impl;

import com.medicalappointment.dao.PatientDAO;
import com.medicalappointment.dao.UserDAO;
import com.medicalappointment.dao.impl.PatientDAOImpl;
import com.medicalappointment.dao.impl.UserDAOImpl;
import com.medicalappointment.exception.AuthenticationException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.model.Patient;
import com.medicalappointment.model.User;
import com.medicalappointment.service.AuthenticationService;
import com.medicalappointment.util.PasswordUtil;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final int PATIENT_ROLE_ID = 3; // matches the ordering inserted in the schema seed data

    private final UserDAO userDAO = new UserDAOImpl();
    private final PatientDAO patientDAO = new PatientDAOImpl();

    @Override
    public User login(String email, String password) throws AuthenticationException, SQLException {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new AuthenticationException("Email and password are required.");
        }
        User user = userDAO.findByEmail(email.trim());
        if (user == null || !PasswordUtil.matches(password, user.getPasswordHash())) {
            throw new AuthenticationException("Invalid email or password.");
        }
        if (!user.isActive()) {
            throw new AuthenticationException("This account has been deactivated. Please contact support.");
        }
        userDAO.updateLastLogin(user.getUserId());
        return user;
    }

    @Override
    public int registerPatient(String firstName, String lastName, String email, String phoneNumber,
                                String password, String confirmPassword)
            throws ValidationException, SQLException {

        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
            throw new ValidationException("First and last name are required.");
        }
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ValidationException("Please provide a valid email address.");
        }
        if (!password.equals(confirmPassword)) {
            throw new ValidationException("Password and confirmation do not match.");
        }
        if (!PasswordUtil.isStrongEnough(password)) {
            throw new ValidationException("Password must be at least 8 characters long.");
        }
        if (userDAO.emailExists(email.trim())) {
            throw new ValidationException("An account with this email already exists.");
        }

        User user = new User();
        user.setRoleId(PATIENT_ROLE_ID);
        user.setFirstName(firstName.trim());
        user.setLastName(lastName.trim());
        user.setEmail(email.trim());
        user.setPhoneNumber(phoneNumber);
        user.setPasswordHash(PasswordUtil.hash(password));
        user.setActive(true);

        int userId = userDAO.createUser(user);

        Patient patient = new Patient();
        patient.setUserId(userId);
        patientDAO.createPatient(patient);

        return userId;
    }

    @Override
    public void changePassword(int userId, String currentPassword, String newPassword, String confirmPassword)
            throws AuthenticationException, ValidationException, SQLException {

        User user = userDAO.findById(userId);
        if (user == null) {
            throw new AuthenticationException("Account not found.");
        }
        if (!PasswordUtil.matches(currentPassword, user.getPasswordHash())) {
            throw new AuthenticationException("Current password is incorrect.");
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new ValidationException("New password and confirmation do not match.");
        }
        if (!PasswordUtil.isStrongEnough(newPassword)) {
            throw new ValidationException("New password must be at least 8 characters long.");
        }
        userDAO.updatePassword(userId, PasswordUtil.hash(newPassword));
    }
}