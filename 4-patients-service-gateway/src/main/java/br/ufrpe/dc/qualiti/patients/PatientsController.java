package br.ufrpe.dc.qualiti.patients;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
public class PatientsController {

    @GetMapping
    public String hello() {
        return "Hello World from Patients Service!";
    }
}
