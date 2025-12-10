package br.ufrpe.dc.qualiti.patients;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<Patient> findAll() {
        return repository.findAll();
    }

    public Patient findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
    }

    public Patient create(Patient patient) {
        patient.setId(null);
        return repository.save(patient);
    }

    public Patient update(Long id, Patient updated) {
        Patient existing = findById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setBirthDate(updated.getBirthDate());
        return repository.save(existing);
    }

    public void delete(Long id) {
        Patient existing = findById(id);
        repository.delete(existing);
    }
}
