package com.pucminas.bubble_app_back.core.studentpackage;

import com.pucminas.bubble_app_back.dataprovider.studentpackage.ISutdentPackageRepository;
import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import com.pucminas.bubble_app_back.model.studentpackage.StudentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentPackageService {
    @Autowired
    ISutdentPackageRepository pacotesDoAlunoRepository;

    public List<Package> obterPacotesPorAluno(String emailAluno) {
        return pacotesDoAlunoRepository.findByStudentEmail(emailAluno).stream()
                .map(StudentPackage::getStudentPackage)
                .toList();
    }

    public List<StudentPackage> saveAll(List<StudentPackage> studentPackages) {
        return pacotesDoAlunoRepository.saveAll(studentPackages);
    }


}
