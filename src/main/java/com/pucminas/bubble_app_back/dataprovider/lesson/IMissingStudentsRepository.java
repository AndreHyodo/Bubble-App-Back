package com.pucminas.bubble_app_back.dataprovider.lesson;

import com.pucminas.bubble_app_back.model.classchack.MissingStudents;
import com.pucminas.bubble_app_back.model.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IMissingStudentsRepository extends JpaRepository<MissingStudents, Long>{

    @Query("SELECT ms FROM MissingStudents ms WHERE ms.lesson.lessonId IN :lessonIds")
    List<MissingStudents> findByLessonIds(@Param("lessonIds") List<Long> lessonIds);

    List<MissingStudents> findByLesson(Lesson lessonId);

}
