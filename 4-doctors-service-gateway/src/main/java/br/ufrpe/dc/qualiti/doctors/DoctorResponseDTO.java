package br.ufrpe.dc.qualiti.doctors;

public class DoctorResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String specialty;

    public DoctorResponseDTO() {
    }

    public DoctorResponseDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.email = doctor.getEmail();
        this.specialty = doctor.getSpecialty();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
