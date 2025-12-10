package br.ufrpe.dc.qualiti.doctors;

public class DoctorRequestDTO {

    private String name;
    private String email;
    private String specialty;

    public DoctorRequestDTO() {
    }

    public DoctorRequestDTO(String name, String email, String specialty) {
        this.name = name;
        this.email = email;
        this.specialty = specialty;
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
