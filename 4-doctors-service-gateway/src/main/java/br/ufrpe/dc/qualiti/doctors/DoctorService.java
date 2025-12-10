package br.ufrpe.dc.qualiti.doctors;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DoctorService {

    private final DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    public List<Doctor> findAll() {
        return repository.findAll();
    }

    public Doctor findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));
    }

    public Doctor create(Doctor doctor) {
        doctor.setId(null);
        return repository.save(doctor);
    }

    public Doctor update(Long id, Doctor updated) {
        Doctor existing = findById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setSpecialty(updated.getSpecialty());
        return repository.save(existing);
    }

    public void delete(Long id) {
        Doctor existing = findById(id);
        repository.delete(existing);
    }
}
