package br.ufrpe.dc.qualiti.doctors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DoctorService {

    private final DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    public List<DoctorResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(DoctorResponseDTO::new)
                .collect(Collectors.toList());
    }

    public DoctorResponseDTO findById(Long id) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));
        return new DoctorResponseDTO(doctor);
    }

    public DoctorResponseDTO create(DoctorRequestDTO request) {
        validateDoctorRequest(request);
        
        Doctor doctor = new Doctor();
        doctor.setName(request.getName());
        doctor.setEmail(request.getEmail());
        doctor.setSpecialty(request.getSpecialty());
        
        Doctor saved = repository.save(doctor);
        return new DoctorResponseDTO(saved);
    }

    public DoctorResponseDTO update(Long id, DoctorRequestDTO request) {
        validateDoctorRequest(request);
        
        Doctor existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));
        
        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        existing.setSpecialty(request.getSpecialty());
        
        Doctor updated = repository.save(existing);
        return new DoctorResponseDTO(updated);
    }

    public void delete(Long id) {
        Doctor existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));
        repository.delete(existing);
    }

    private void validateDoctorRequest(DoctorRequestDTO request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        if (request.getSpecialty() == null || request.getSpecialty().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Specialty is required");
        }
    }
}
