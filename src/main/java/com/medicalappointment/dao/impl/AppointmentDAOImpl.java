// File: src/com/medicalappointment/dao/impl/AppointmentDAOImpl.java
package com.medicalappointment.dao.impl;

import com.medicalappointment.dao.AppointmentDAO;
import com.medicalappointment.model.Appointment;
import com.medicalappointment.model.AppointmentStatus;
import com.medicalappointment.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAOImpl implements AppointmentDAO {

    private static final String SELECT_BASE =
        "SELECT a.appointment_id, a.patient_id, a.doctor_id, a.appointment_date, a.start_time, " +
        "a.end_time, a.reason, a.status, a.rejection_reason, a.cancellation_reason, " +
        "a.rescheduled_from_id, a.created_at, a.updated_at, " +
        "pu.first_name AS patient_first_name, pu.last_name AS patient_last_name, " +
        "pu.phone_number AS patient_phone_number, " +
        "du.first_name AS doctor_first_name, du.last_name AS doctor_last_name, " +
        "dep.department_name, spec.specialty_name " +
        "FROM appointments a " +
        "JOIN patients p ON a.patient_id = p.patient_id " +
        "JOIN users pu ON p.user_id = pu.user_id " +
        "JOIN doctors doc ON a.doctor_id = doc.doctor_id " +
        "JOIN users du ON doc.user_id = du.user_id " +
        "LEFT JOIN departments dep ON doc.department_id = dep.department_id " +
        "LEFT JOIN specialties spec ON doc.specialty_id = spec.specialty_id ";

    /**
     * Inserts the appointment. The partial unique index ux_doctor_slot_active on the
     * appointments table is the authoritative double booking guard: if another request
     * won the race between the availability check in the service layer and this insert,
     * PostgreSQL raises a unique_violation (SQLState 23505), which is caught here and
     * re-thrown as a SQLException carrying that same SQLState so the service layer can
     * translate it into a friendly "slot no longer available" message.
     */
    @Override
    public int bookAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, start_time, " +
                     "end_time, reason, status) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING appointment_id";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointment.getPatientId());
            ps.setInt(2, appointment.getDoctorId());
            ps.setDate(3, appointment.getAppointmentDate());
            ps.setTime(4, appointment.getStartTime());
            ps.setTime(5, appointment.getEndTime());
            ps.setString(6, appointment.getReason());
            ps.setString(7, appointment.getStatus().name());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("appointment_id");
                }
            }
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new SQLException("This appointment slot has just been booked by another patient. " +
                        "Please choose a different time.", "23505", e);
            }
            throw e;
        }
        return -1;
    }

    @Override
    public boolean isSlotTaken(int doctorId, Date date, Time startTime) throws SQLException {
        String sql = "SELECT 1 FROM appointments WHERE doctor_id = ? AND appointment_date = ? " +
                     "AND start_time = ? AND status IN ('PENDING', 'CONFIRMED', 'RESCHEDULED')";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setDate(2, date);
            ps.setTime(3, startTime);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public Appointment findById(int appointmentId) throws SQLException {
        String sql = SELECT_BASE + "WHERE a.appointment_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Appointment> findByPatient(int patientId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE a.patient_id = ? ORDER BY a.appointment_date DESC, a.start_time DESC";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapRow(rs));
                }
            }
        }
        return appointments;
    }

    @Override
    public List<Appointment> findByDoctor(int doctorId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE a.doctor_id = ? ORDER BY a.appointment_date DESC, a.start_time DESC";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapRow(rs));
                }
            }
        }
        return appointments;
    }

    @Override
    public List<Appointment> findByDoctorAndDate(int doctorId, Date date) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE a.doctor_id = ? AND a.appointment_date = ? ORDER BY a.start_time";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setDate(2, date);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapRow(rs));
                }
            }
        }
        return appointments;
    }

    @Override
    public List<Appointment> findAll() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = SELECT_BASE + "ORDER BY a.appointment_date DESC, a.start_time DESC";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                appointments.add(mapRow(rs));
            }
        }
        return appointments;
    }

    @Override
    public List<Appointment> filterAppointments(Integer doctorId, Integer patientId, String status,
                                                 Date fromDate, Date toDate) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        StringBuilder sql = new StringBuilder(SELECT_BASE).append("WHERE 1 = 1 ");
        List<Object> params = new ArrayList<>();

        if (doctorId != null) {
            sql.append("AND a.doctor_id = ? ");
            params.add(doctorId);
        }
        if (patientId != null) {
            sql.append("AND a.patient_id = ? ");
            params.add(patientId);
        }
        if (status != null && !status.isBlank()) {
            sql.append("AND a.status = ? ");
            params.add(status);
        }
        if (fromDate != null) {
            sql.append("AND a.appointment_date >= ? ");
            params.add(fromDate);
        }
        if (toDate != null) {
            sql.append("AND a.appointment_date <= ? ");
            params.add(toDate);
        }
        sql.append("ORDER BY a.appointment_date DESC, a.start_time DESC");

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapRow(rs));
                }
            }
        }
        return appointments;
    }

    @Override
    public boolean updateStatus(int appointmentId, String status, String rejectionReason,
                                 String cancellationReason) throws SQLException {
        String sql = "UPDATE appointments SET status = ?, rejection_reason = ?, cancellation_reason = ? " +
                     "WHERE appointment_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, rejectionReason);
            ps.setString(3, cancellationReason);
            ps.setInt(4, appointmentId);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public int countByStatus(String status) throws SQLException {
        String sql = "SELECT COUNT(*) FROM appointments WHERE status = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    @Override
    public int countAllAppointments() throws SQLException {
        String sql = "SELECT COUNT(*) FROM appointments";
        try (Connection conn = DBConnectionUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    @Override
    public int countTodayForDoctor(int doctorId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = CURRENT_DATE " +
                     "AND status IN ('PENDING', 'CONFIRMED')";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    @Override
    public int countUpcomingForPatient(int patientId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM appointments WHERE patient_id = ? " +
                     "AND appointment_date >= CURRENT_DATE AND status IN ('PENDING', 'CONFIRMED')";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    private Appointment mapRow(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(rs.getInt("appointment_id"));
        appointment.setPatientId(rs.getInt("patient_id"));
        appointment.setDoctorId(rs.getInt("doctor_id"));
        appointment.setAppointmentDate(rs.getDate("appointment_date"));
        appointment.setStartTime(rs.getTime("start_time"));
        appointment.setEndTime(rs.getTime("end_time"));
        appointment.setReason(rs.getString("reason"));
        appointment.setStatus(AppointmentStatus.valueOf(rs.getString("status")));
        appointment.setRejectionReason(rs.getString("rejection_reason"));
        appointment.setCancellationReason(rs.getString("cancellation_reason"));
        appointment.setRescheduledFromId((Integer) rs.getObject("rescheduled_from_id"));
        appointment.setCreatedAt(rs.getTimestamp("created_at"));
        appointment.setUpdatedAt(rs.getTimestamp("updated_at"));
        appointment.setPatientFirstName(rs.getString("patient_first_name"));
        appointment.setPatientLastName(rs.getString("patient_last_name"));
        appointment.setPatientPhoneNumber(rs.getString("patient_phone_number"));
        appointment.setDoctorFirstName(rs.getString("doctor_first_name"));
        appointment.setDoctorLastName(rs.getString("doctor_last_name"));
        appointment.setDepartmentName(rs.getString("department_name"));
        appointment.setSpecialtyName(rs.getString("specialty_name"));
        return appointment;
    }
}