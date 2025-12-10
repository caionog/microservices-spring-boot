package br.ufrpe.dc.qualiti.appointment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppointmentCreatedListener {

    private final NotificationIntegrationService notificationIntegrationService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onAppointmentCreated(AppointmentCreatedEvent event) {
        log.info("🔔 AppointmentCreatedListener invoked for appointment {}", event.getAppointmentId());
        try {
            String subject = "Confirmação de Agendamento";
            String body = String.format(
                    "Olá %s, sua consulta com Dr(a). %s foi confirmada para %s. Motivo: %s.",
                    event.getPatientName(),
                    event.getDoctorName(),
                    event.getAppointmentDate(),
                    event.getReason()
            );

            EmailRequest request = new EmailRequest(event.getPatientEmail(), subject, body);
            log.info("📨 Calling notifyAppointment for email: {}", event.getPatientEmail());
            notificationIntegrationService.notifyAppointment(request);
            log.info("✅ notifyAppointment completed successfully");
        } catch (Exception e) {
            log.error("❌ Error in AppointmentCreatedListener.onAppointmentCreated: {}", e.getMessage(), e);
        }
    }
}
