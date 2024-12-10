package com.pucminas.bubble_app_back.dataprovider.studentpackage;

import com.pucminas.bubble_app_back.model.studentpackage.StudentPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISutdentPackageRepository extends JpaRepository<StudentPackage, Long> {

    List<StudentPackage> findByStudentEmail(String email);

}
