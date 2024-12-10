package com.pucminas.bubble_app_back.dataprovider.lessonpackage;

import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPackageRepository extends JpaRepository<Package, Integer> {
    List<Package> findByTeacherEmail(String emailProfessor);
}
