package br.ufrpe.dc.qualiti.patients;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<PatientResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(PatientResponseDTO::new)
                .collect(Collectors.toList());
    }

    public PatientResponseDTO findById(Long id) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
        return new PatientResponseDTO(patient);
    }

    public PatientResponseDTO create(PatientRequestDTO request) {
        validatePatientRequest(request);
        
        Patient patient = new Patient();
        patient.setName(request.getName());
        patient.setEmail(request.getEmail());
        patient.setPhone(request.getPhone());
        patient.setCpf(request.getCpf());
        patient.setBirthDate(request.getBirthDate());
        
        Patient saved = repository.save(patient);
        return new PatientResponseDTO(saved);
    }

    public PatientResponseDTO update(Long id, PatientRequestDTO request) {
        validatePatientRequest(request);
        
        Patient existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
        
        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        existing.setPhone(request.getPhone());
        existing.setCpf(request.getCpf());
        existing.setBirthDate(request.getBirthDate());
        
        Patient updated = repository.save(existing);
        return new PatientResponseDTO(updated);
    }

    public void delete(Long id) {
        Patient existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
        repository.delete(existing);
    }

    private void validatePatientRequest(PatientRequestDTO request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        if (request.getCpf() == null || request.getCpf().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF is required");
        }
        if (!request.getCpf().matches("\\d{11}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF must contain exactly 11 digits");
        }
    }
}
