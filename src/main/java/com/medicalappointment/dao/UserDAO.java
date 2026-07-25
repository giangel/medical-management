// File: src/com/medicalappointment/dao/UserDAO.java
package com.medicalappointment.dao;

import com.medicalappointment.model.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    int createUser(User user) throws SQLException;
    User findById(int userId) throws SQLException;
    User findByEmail(String email) throws SQLException;
    boolean emailExists(String email) throws SQLException;
    boolean updateUser(User user) throws SQLException;
    boolean updatePassword(int userId, String newPasswordHash) throws SQLException;
    boolean updateLastLogin(int userId) throws SQLException;
    boolean setActive(int userId, boolean active) throws SQLException;
    List<User> findAll() throws SQLException;
    List<User> findByRoleName(String roleName) throws SQLException;
    int countByRoleName(String roleName) throws SQLException;
    int countAllUsers() throws SQLException;
}