package br.ufrpe.dc.qualiti.appointment;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDTO {
        @NotNull(message = "Doctor ID is required")
        private Long doctorId;

        @NotNull(message = "Patient ID is required")
        private Long patientId;

        @NotNull(message = "Appointment date is required")
        private LocalDateTime appointmentDate;

        private String reason;
}
