// File: src/com/medicalappointment/service/UserService.java
package com.medicalappointment.service;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    User getById(int userId) throws SQLException, ResourceNotFoundException;
    List<User> getAll() throws SQLException;
    List<User> getByRole(String roleName) throws SQLException;
    void updateProfile(User user) throws SQLException;
    void setAccountActive(int userId, boolean active) throws SQLException;
    int countTotalUsers() throws SQLException;
}