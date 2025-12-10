package br.ufrpe.dc.qualiti.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void send(EmailDTO dto) {
        log.info("Sending email to: {}", dto.getTo());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@sistema-agendamento.com");
            message.setTo(dto.getTo());
            message.setSubject(dto.getSubject());
            message.setText(dto.getBody());
            mailSender.send(message);
            log.info("Email sent to: {}", dto.getTo());
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", dto.getTo(), e.getMessage());
            throw new RuntimeException("Erro no envio de email", e);
        }
    }
}
