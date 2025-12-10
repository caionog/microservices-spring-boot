package br.ufrpe.dc.qualiti.patients;

import java.time.LocalDate;

public class PatientRequestDTO {

    private String name;
    private String email;
    private String phone;
    private String cpf;
    private LocalDate birthDate;

    public PatientRequestDTO() {
    }

    public PatientRequestDTO(String name, String email, String phone, String cpf, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
        this.birthDate = birthDate;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
