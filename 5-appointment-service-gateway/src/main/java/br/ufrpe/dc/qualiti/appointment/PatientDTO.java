package br.ufrpe.dc.qualiti.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
        private Long id;
        private String name;
        private String cpf;
}
