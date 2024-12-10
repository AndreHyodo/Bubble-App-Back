package com.pucminas.bubble_app_back.dataprovider.lesson;

import com.pucminas.bubble_app_back.model.classchack.MissingStudents;
import com.pucminas.bubble_app_back.model.classchack.StudentsAttendance;
import com.pucminas.bubble_app_back.model.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IStudentsAttendanceRepository extends JpaRepository<StudentsAttendance, Long> {

    @Query("SELECT ms FROM StudentsAttendance ms WHERE ms.lesson.lessonId IN :lessonIds")
    List<StudentsAttendance> findByLessonIds(@Param("lessonIds") List<Long> lessonIds);

    List<StudentsAttendance> findByLesson(Lesson lessonId);
}
