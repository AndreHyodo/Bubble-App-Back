package com.pucminas.bubble_app_back.usecase.lessonpackage;

import com.pucminas.bubble_app_back.core.lessonpackage.PackageService;
import com.pucminas.bubble_app_back.core.studentpackage.StudentPackageService;
import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetPackageUseCase {
    @Autowired
    PackageService packageService;

    @Autowired
    StudentPackageService studentPackageService;

    public List<Package> getStudentPackages(String emailAluno) {
        return studentPackageService.obterPacotesPorAluno(emailAluno).stream().filter(Package::getIsActive).toList();
    }

    public List<Package> getTeacherPackages(String emailProfessor) {
        return packageService.obterPacotesPorProfessor(emailProfessor).stream().filter(Package::getIsActive).toList();
    }

}


