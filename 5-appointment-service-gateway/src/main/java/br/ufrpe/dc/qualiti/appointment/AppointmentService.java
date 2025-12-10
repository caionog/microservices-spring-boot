package br.ufrpe.dc.qualiti.appointment;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService {

    private final AppointmentRepository repository;
    private final DoctorClient doctorClient;
    private final PatientClient patientClient;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public AppointmentResponseDTO create(AppointmentRequestDTO request) {
        DoctorDTO doctor;
        PatientDTO patient;
        try {
            doctor = doctorClient.getDoctorById(request.getDoctorId());
        } catch (FeignException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        }

        try {
            patient = patientClient.getPatientById(request.getPatientId());
        } catch (FeignException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        }

        // 3. Regra: Data deve ser no futuro
        if (request.getAppointmentDate().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment date must be in the future");
        }

        // 4. Validar Disponibilidade (Conflito de Agenda - Double Booking)
        boolean conflict = repository.existsByDoctorIdAndAppointmentDateAndStatus(
                request.getDoctorId(),
                request.getAppointmentDate(),
                AppointmentStatus.SCHEDULED
        );
        if (conflict) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Doctor already has an appointment at this time");
        }

        // 5. Criar e Salvar
        Appointment entity = new Appointment();
        entity.setDoctorId(request.getDoctorId());
        entity.setPatientId(request.getPatientId());
        entity.setAppointmentDate(request.getAppointmentDate());
        entity.setReason(request.getReason());
        entity.setStatus(AppointmentStatus.SCHEDULED);

        Appointment saved = repository.save(entity);

        // Publica evento após persistir; listener envia notificação pós-commit
        AppointmentCreatedEvent event = new AppointmentCreatedEvent(
            saved.getId(),
            doctor.getId(),
            doctor.getName(),
            patient.getId(),
            patient.getName(),
            patient.getEmail(),
            saved.getAppointmentDate(),
            saved.getReason()
        );
        log.info("📤 Publishing AppointmentCreatedEvent for ID {} to patient email: {}", 
            saved.getId(), patient.getEmail());
        eventPublisher.publishEvent(event);
        log.info("✅ AppointmentCreatedEvent published successfully");

        return AppointmentResponseDTO.from(saved);
    }

    public AppointmentResponseDTO findById(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));
        return AppointmentResponseDTO.from(appointment);
    }

    public List<AppointmentResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(AppointmentResponseDTO::from)
                .collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> findByDoctorId(Long doctorId) {
        return repository.findByDoctorId(doctorId).stream()
                .map(AppointmentResponseDTO::from)
                .collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> findByPatientId(Long patientId) {
        return repository.findByPatientId(patientId).stream()
                .map(AppointmentResponseDTO::from)
                .collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> findByStatus(AppointmentStatus status) {
        return repository.findByStatus(status).stream()
                .map(AppointmentResponseDTO::from)
                .collect(Collectors.toList());
    }

    public AppointmentResponseDTO update(Long id, AppointmentRequestDTO request) {
        Appointment existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));

        // Validar doctor
        try {
            doctorClient.getDoctorById(request.getDoctorId());
        } catch (FeignException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        }

        // Validar patient
        try {
            patientClient.getPatientById(request.getPatientId());
        } catch (FeignException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        }

        // Validar data futura
        if (request.getAppointmentDate().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment date must be in the future");
        }

        // Validar disponibilidade (permitir se for o mesmo horário do appointment sendo atualizado)
        if (!existing.getAppointmentDate().equals(request.getAppointmentDate())) {
            boolean conflict = repository.existsByDoctorIdAndAppointmentDateAndStatus(
                    request.getDoctorId(),
                    request.getAppointmentDate(),
                    AppointmentStatus.SCHEDULED
            );
            if (conflict) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Doctor already has an appointment at this time");
            }
        }

        existing.setDoctorId(request.getDoctorId());
        existing.setPatientId(request.getPatientId());
        existing.setAppointmentDate(request.getAppointmentDate());
        existing.setReason(request.getReason());

        Appointment updated = repository.save(existing);
        return AppointmentResponseDTO.from(updated);
    }

    public void delete(Long id) {
        Appointment existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));
        repository.delete(existing);
    }

    public void cancel(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));
        
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment is already cancelled");
        }
        
        appointment.setStatus(AppointmentStatus.CANCELLED);
        repository.save(appointment);
    }

    public void complete(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));
        
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot complete a cancelled appointment");
        }
        
        appointment.setStatus(AppointmentStatus.COMPLETED);
        repository.save(appointment);
    }
}
