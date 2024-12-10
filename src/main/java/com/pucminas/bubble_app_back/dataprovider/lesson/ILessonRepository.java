package com.pucminas.bubble_app_back.dataprovider.lesson;

import com.pucminas.bubble_app_back.model.lesson.Lesson;
import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ILessonRepository extends JpaRepository<Lesson, Long> {

//    List<Lesson> findByStudentEmail(String email);

    List<Lesson> findByLessonPackagePackageId(Long lessonPackage);

}
