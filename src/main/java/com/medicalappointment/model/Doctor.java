// File: src/com/medicalappointment/model/Doctor.java
package com.medicalappointment.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Doctor {

    private int doctorId;
    private int userId;
    private Integer departmentId;
    private Integer specialtyId;
    private String licenseNumber;
    private Integer yearsOfExperience;
    private String biography;
    private String qualifications;
    private BigDecimal consultationFee;
    private int defaultSlotMinutes;
    private boolean acceptingAppointments;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Convenience fields populated by joins, not columns on this table
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String profileImage;
    private String departmentName;
    private String specialtyName;

    public Doctor() {
    }

    public Doctor(int doctorId, int userId, Integer departmentId, Integer specialtyId, String licenseNumber,
                  Integer yearsOfExperience, String biography, String qualifications,
                  BigDecimal consultationFee, int defaultSlotMinutes, boolean acceptingAppointments) {
        this.doctorId = doctorId;
        this.userId = userId;
        this.departmentId = departmentId;
        this.specialtyId = specialtyId;
        this.licenseNumber = licenseNumber;
        this.yearsOfExperience = yearsOfExperience;
        this.biography = biography;
        this.qualifications = qualifications;
        this.consultationFee = consultationFee;
        this.defaultSlotMinutes = defaultSlotMinutes;
        this.acceptingAppointments = acceptingAppointments;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Integer specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(BigDecimal consultationFee) {
        this.consultationFee = consultationFee;
    }

    public int getDefaultSlotMinutes() {
        return defaultSlotMinutes;
    }

    public void setDefaultSlotMinutes(int defaultSlotMinutes) {
        this.defaultSlotMinutes = defaultSlotMinutes;
    }

    public boolean isAcceptingAppointments() {
        return acceptingAppointments;
    }

    public void setAcceptingAppointments(boolean acceptingAppointments) {
        this.acceptingAppointments = acceptingAppointments;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return "Dr. " + firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    @Override
    public String toString() {
        return "Doctor{doctorId=" + doctorId + ", licenseNumber=" + licenseNumber + "}";
    }
}