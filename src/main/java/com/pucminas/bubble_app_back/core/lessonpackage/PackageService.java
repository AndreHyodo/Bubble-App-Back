package com.pucminas.bubble_app_back.core.lessonpackage;

import com.pucminas.bubble_app_back.dataprovider.lessonpackage.IPackageRepository;
import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {
    @Autowired
    IPackageRepository IPackageRepository;

    public Package salvaNovoPacote(Package aPackage) {
        return IPackageRepository.save(aPackage);
    }

    public List<Package> obterPacotesPorProfessor(String teacherEmail){
        return IPackageRepository.findByTeacherEmail(teacherEmail);
    }

    public Package obterPorId(Integer id){
        return IPackageRepository.findById(id).get();
    }
}
