// File: src/com/medicalappointment/util/InputSanitizer.java
package com.medicalappointment.util;

/**
 * Escapes user-supplied text before it is echoed back into JSP output, as a
 * defense-in-depth measure against stored and reflected XSS. JSTL's
 * <c:out> already escapes by default, so this is a second safety net for
 * any place raw ${} EL output is ever used instead of <c:out>.
 */
public final class InputSanitizer {

    private InputSanitizer() {
    }

    public static String escapeHtml(String input) {
        if (input == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(input.length());
        for (char c : input.toCharArray()) {
            switch (c) {
                case '<': sb.append("&lt;"); break;
                case '>': sb.append("&gt;"); break;
                case '&': sb.append("&amp;"); break;
                case '"': sb.append("&quot;"); break;
                case '\'': sb.append("&#x27;"); break;
                default: sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String trimToNull(String input) {
        if (input == null) {
            return null;
        }
        String trimmed = input.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}