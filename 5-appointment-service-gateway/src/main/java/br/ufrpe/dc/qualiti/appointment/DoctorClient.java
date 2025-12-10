package br.ufrpe.dc.qualiti.appointment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctors")
public interface DoctorClient {

    @GetMapping("/doctors/{id}")
    DoctorDTO getDoctorById(@PathVariable("id") Long id);
}
