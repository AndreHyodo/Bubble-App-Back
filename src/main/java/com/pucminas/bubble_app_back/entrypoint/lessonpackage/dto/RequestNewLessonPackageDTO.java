package com.pucminas.bubble_app_back.entrypoint.lessonpackage.dto;

import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import lombok.Data;

import java.util.List;

@Data
public class RequestNewLessonPackageDTO {
    String emailProfessor;
    Integer packageDaTurma;
    List<String> emailAlunos;

}
