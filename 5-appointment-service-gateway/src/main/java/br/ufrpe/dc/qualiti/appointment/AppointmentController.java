package br.ufrpe.dc.qualiti.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponseDTO create(@RequestBody @Valid AppointmentRequestDTO request) {
        return service.create(request);
    }

    @GetMapping
    public List<AppointmentResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public AppointmentResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<AppointmentResponseDTO> findByDoctor(@PathVariable Long doctorId) {
        return service.findByDoctorId(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public List<AppointmentResponseDTO> findByPatient(@PathVariable Long patientId) {
        return service.findByPatientId(patientId);
    }

    @GetMapping("/status/{status}")
    public List<AppointmentResponseDTO> findByStatus(@PathVariable AppointmentStatus status) {
        return service.findByStatus(status);
    }

    @PutMapping("/{id}")
    public AppointmentResponseDTO update(@PathVariable Long id, @RequestBody @Valid AppointmentRequestDTO request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public void cancel(@PathVariable Long id) {
        service.cancel(id);
    }

    @PutMapping("/{id}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void complete(@PathVariable Long id) {
        service.complete(id);
    }
}
