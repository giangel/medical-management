// File: src/com/medicalappointment/util/SessionConstants.java
package com.medicalappointment.util;

/**
 * Centralizes every session attribute key used across the application so a
 * typo in one servlet cannot silently break another.
 */
public final class SessionConstants {

    private SessionConstants() {
    }

    public static final String USER_ID = "userId";
    public static final String USER_FULL_NAME = "userFullName";
    public static final String USER_ROLE = "userRole"; // ADMIN, DOCTOR, or PATIENT
    public static final String USER_EMAIL = "userEmail";

    // Populated on login for DOCTOR and PATIENT roles so servlets do not
    // need to re-look-up the doctor_id/patient_id on every request.
    public static final String DOCTOR_ID = "doctorId";
    public static final String PATIENT_ID = "patientId";
}