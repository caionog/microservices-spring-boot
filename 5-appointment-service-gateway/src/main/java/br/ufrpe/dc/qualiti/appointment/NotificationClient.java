package br.ufrpe.dc.qualiti.appointment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification")
public interface NotificationClient {

    @PostMapping("/emails/send")
    void sendEmail(@RequestBody EmailRequest request);
}
