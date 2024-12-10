package com.pucminas.bubble_app_back.model.studentpackage;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import com.pucminas.bubble_app_back.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity(name = "PacotesDoAluno")
@NoArgsConstructor
@AllArgsConstructor
public class StudentPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long tableId;

    @ManyToOne
    @JoinColumn(name = "aluno_email")
    User student;

    @ManyToOne
    @JoinColumn(name = "pacote_id_pacote")
    @JsonBackReference
    Package studentPackage;
}
