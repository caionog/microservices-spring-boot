package br.ufrpe.dc.qualiti.appointment;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationIntegrationService {

    private static final String CB_NAME = "notification";

    private final NotificationClient notificationClient;

    @CircuitBreaker(name = CB_NAME, fallbackMethod = "fallback")
    public void notifyAppointment(EmailRequest request) {
        log.info("📧 Enviando notificação para: {}", request.getTo());
        notificationClient.sendEmail(request);
        log.info("✅ Notificação enviada com sucesso para: {}", request.getTo());
    }

    // Fallback: não propaga exceção para não quebrar o agendamento
    public void fallback(EmailRequest request, Throwable t) {
        log.error("⚠️ Falha ao enviar notificação para {}: {}", 
            request.getTo(), t.getMessage());
    }
}
