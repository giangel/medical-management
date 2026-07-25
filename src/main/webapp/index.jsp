<%-- File: WebContent/index.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meridian Health - Book Appointments With Trusted Doctors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-public.jsp"/>

<section class="band">
    <div class="container" style="display:flex; gap: var(--space-8); align-items:center; flex-wrap: wrap;">
        <div style="flex: 1 1 480px;">
            <h1>Healthcare that moves at your pace, not a waiting room's.</h1>
            <p style="font-size: var(--text-lg); color: var(--color-ink-muted); max-width: 520px;">
                Meridian Health connects you with the right doctor, on a time that actually works for you,
                without the phone calls, hold music, or paper forms.
            </p>
            <div style="display:flex; gap: var(--space-3); margin-top: var(--space-5);">
                <a class="btn btn--primary" href="${pageContext.request.contextPath}/register">Book Your First Appointment</a>
                <a class="btn btn--secondary" href="${pageContext.request.contextPath}/login">I Already Have an Account</a>
            </div>
        </div>
        <div style="flex: 1 1 360px;">
            <div class="card">
                <h3>How it works</h3>
                <ol style="padding-left: var(--space-5); color: var(--color-ink-muted);">
                    <li>Search by doctor name, specialty, or department.</li>
                    <li>View real availability and pick a time that suits you.</li>
                    <li>Get a confirmation the moment your doctor accepts.</li>
                </ol>
            </div>
        </div>
    </div>
</section>

<section class="band band--alt">
    <div class="container">
        <h2>Care across every specialty you need</h2>
        <div class="grid grid--4" style="margin-top: var(--space-5);">
            <div class="card">
                <h3 style="font-size: var(--text-lg);">Cardiology</h3>
                <p style="color: var(--color-ink-muted); font-size: var(--text-sm);">Heart health, from routine checkups to specialist referrals.</p>
            </div>
            <div class="card">
                <h3 style="font-size: var(--text-lg);">Pediatrics</h3>
                <p style="color: var(--color-ink-muted); font-size: var(--text-sm);">Attentive, age appropriate care for infants through teens.</p>
            </div>
            <div class="card">
                <h3 style="font-size: var(--text-lg);">Orthopedics</h3>
                <p style="color: var(--color-ink-muted); font-size: var(--text-sm);">Joint, bone, and sports injury specialists.</p>
            </div>
            <div class="card">
                <h3 style="font-size: var(--text-lg);">General Medicine</h3>
                <p style="color: var(--color-ink-muted); font-size: var(--text-sm);">Everyday primary care for the whole family.</p>
            </div>
        </div>
    </div>
</section>

<section class="band">
    <div class="container">
        <h2>Why patients choose Meridian</h2>
        <div class="grid grid--3" style="margin-top: var(--space-5);">
            <div>
                <h3 style="font-size: var(--text-lg);">Real availability</h3>
                <p style="color: var(--color-ink-muted);">See a doctor's actual open slots, not a generic request form that gets answered days later.</p>
            </div>
            <div>
                <h3 style="font-size: var(--text-lg);">No double bookings</h3>
                <p style="color: var(--color-ink-muted);">Every slot is protected the moment it's booked, so you never lose a spot to a scheduling clash.</p>
            </div>
            <div>
                <h3 style="font-size: var(--text-lg);">Stay in the loop</h3>
                <p style="color: var(--color-ink-muted);">Confirmations, reschedules, and reminders land in your notifications automatically.</p>
            </div>
        </div>
    </div>
</section>

<section class="band band--alt">
    <div class="container" style="text-align:center;">
        <h2>Ready to book your next appointment?</h2>
        <p style="color: var(--color-ink-muted);">It takes less than two minutes to create an account.</p>
        <a class="btn btn--primary" href="${pageContext.request.contextPath}/register">Create a Free Account</a>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>