// File: src/com/medicalappointment/service/impl/UserServiceImpl.java
package com.medicalappointment.service.impl;

import com.medicalappointment.dao.UserDAO;
import com.medicalappointment.dao.impl.UserDAOImpl;
import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.model.User;
import com.medicalappointment.service.UserService;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public User getById(int userId) throws SQLException, ResourceNotFoundException {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found.");
        }
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        return userDAO.findAll();
    }

    @Override
    public List<User> getByRole(String roleName) throws SQLException {
        return userDAO.findByRoleName(roleName);
    }

    @Override
    public void updateProfile(User user) throws SQLException {
        userDAO.updateUser(user);
    }

    @Override
    public void setAccountActive(int userId, boolean active) throws SQLException {
        userDAO.setActive(userId, active);
    }

    @Override
    public int countTotalUsers() throws SQLException {
        return userDAO.countAllUsers();
    }
}