// File: src/com/medicalappointment/util/PasswordUtil.java
package com.medicalappointment.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Wraps jBCrypt so no other class in the project touches a hashing
 * algorithm directly. Requires jbcrypt-0.4.jar on the classpath
 * (WebContent/WEB-INF/lib), covered in the JAR list at the end of Phase 5.
 */
public final class PasswordUtil {

    private static final int WORKLOAD = 10;

    private PasswordUtil() {
    }

    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(WORKLOAD));
    }

    public static boolean matches(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            // Malformed hash in the database, never happens with data written by this app
            return false;
        }
    }

    public static boolean isStrongEnough(String plainPassword) {
        return plainPassword != null && plainPassword.length() >= 8;
    }
}