package br.ufrpe.dc.qualiti.doctors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctors")
public class DoctorsController {

    @GetMapping
    public String hello() {
        return "Hello World from Doctors Service!";
    }
}
