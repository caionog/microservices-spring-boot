package br.ufrpe.dc.qualiti.appointment;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {
        private Long id;
        private Long doctorId;
        private Long patientId;
        private LocalDateTime appointmentDate;
        private AppointmentStatus status;
        private String reason;
        private LocalDateTime createdAt;

    public static AppointmentResponseDTO from(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getDoctorId(),
                appointment.getPatientId(),
                appointment.getAppointmentDate(),
                appointment.getStatus(),
                appointment.getReason(),
                appointment.getCreatedAt()
        );
    }
}
