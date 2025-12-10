package br.ufrpe.dc.qualiti.appointment;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class AppointmentCreatedEvent {
    Long appointmentId;
    Long doctorId;
    String doctorName;
    Long patientId;
    String patientName;
    String patientEmail;
    LocalDateTime appointmentDate;
    String reason;
}
