// File: src/com/medicalappointment/dao/impl/DoctorAvailabilityDAOImpl.java
package com.medicalappointment.dao.impl;

import com.medicalappointment.dao.DoctorAvailabilityDAO;
import com.medicalappointment.model.AvailabilityRecordType;
import com.medicalappointment.model.DoctorAvailability;
import com.medicalappointment.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorAvailabilityDAOImpl implements DoctorAvailabilityDAO {

    @Override
    public int createAvailability(DoctorAvailability availability) throws SQLException {
        String sql = "INSERT INTO doctor_availability (doctor_id, record_type, day_of_week, " +
                     "specific_date, start_time, end_time, is_unavailable, slot_minutes) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING availability_id";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, availability.getDoctorId());
            ps.setString(2, availability.getRecordType().name());
            if (availability.getDayOfWeek() != null) {
                ps.setInt(3, availability.getDayOfWeek());
            } else {
                ps.setNull(3, Types.SMALLINT);
            }
            ps.setDate(4, availability.getSpecificDate());
            ps.setTime(5, availability.getStartTime());
            ps.setTime(6, availability.getEndTime());
            ps.setBoolean(7, availability.isUnavailable());
            ps.setInt(8, availability.getSlotMinutes());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("availability_id");
                }
            }
        }
        return -1;
    }

    @Override
    public List<DoctorAvailability> findRecurringByDoctor(int doctorId) throws SQLException {
        List<DoctorAvailability> results = new ArrayList<>();
        String sql = "SELECT * FROM doctor_availability WHERE doctor_id = ? AND record_type = 'RECURRING' " +
                     "ORDER BY day_of_week, start_time";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
        }
        return results;
    }

    @Override
    public List<DoctorAvailability> findOverridesByDoctor(int doctorId) throws SQLException {
        List<DoctorAvailability> results = new ArrayList<>();
        String sql = "SELECT * FROM doctor_availability WHERE doctor_id = ? AND record_type = 'DATE_OVERRIDE' " +
                     "ORDER BY specific_date";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
        }
        return results;
    }

    @Override
    public DoctorAvailability findOverrideForDate(int doctorId, Date date) throws SQLException {
        String sql = "SELECT * FROM doctor_availability WHERE doctor_id = ? AND record_type = 'DATE_OVERRIDE' " +
                     "AND specific_date = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setDate(2, date);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean updateAvailability(DoctorAvailability availability) throws SQLException {
        String sql = "UPDATE doctor_availability SET day_of_week = ?, specific_date = ?, start_time = ?, " +
                     "end_time = ?, is_unavailable = ?, slot_minutes = ? WHERE availability_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (availability.getDayOfWeek() != null) {
                ps.setInt(1, availability.getDayOfWeek());
            } else {
                ps.setNull(1, Types.SMALLINT);
            }
            ps.setDate(2, availability.getSpecificDate());
            ps.setTime(3, availability.getStartTime());
            ps.setTime(4, availability.getEndTime());
            ps.setBoolean(5, availability.isUnavailable());
            ps.setInt(6, availability.getSlotMinutes());
            ps.setInt(7, availability.getAvailabilityId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteAvailability(int availabilityId) throws SQLException {
        String sql = "DELETE FROM doctor_availability WHERE availability_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, availabilityId);
            return ps.executeUpdate() > 0;
        }
    }

    private DoctorAvailability mapRow(ResultSet rs) throws SQLException {
        DoctorAvailability availability = new DoctorAvailability();
        availability.setAvailabilityId(rs.getInt("availability_id"));
        availability.setDoctorId(rs.getInt("doctor_id"));
        availability.setRecordType(AvailabilityRecordType.valueOf(rs.getString("record_type")));
        availability.setDayOfWeek((Integer) rs.getObject("day_of_week"));
        availability.setSpecificDate(rs.getDate("specific_date"));
        availability.setStartTime(rs.getTime("start_time"));
        availability.setEndTime(rs.getTime("end_time"));
        availability.setUnavailable(rs.getBoolean("is_unavailable"));
        availability.setSlotMinutes(rs.getInt("slot_minutes"));
        availability.setCreatedAt(rs.getTimestamp("created_at"));
        return availability;
    }
}