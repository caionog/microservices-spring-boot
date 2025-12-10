package br.ufrpe.dc.qualiti.doctors;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctors")
public class DoctorsController {

    private final DoctorService service;

    public DoctorsController(DoctorService service) {
        this.service = service;
    }

    @GetMapping
    public List<DoctorResponseDTO> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public DoctorResponseDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponseDTO create(@RequestBody DoctorRequestDTO request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public DoctorResponseDTO update(@PathVariable Long id, @RequestBody DoctorRequestDTO request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
