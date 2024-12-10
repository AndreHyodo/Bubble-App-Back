package com.pucminas.bubble_app_back.core.lesson;

import com.pucminas.bubble_app_back.common.enums.LessonStatus;
import com.pucminas.bubble_app_back.dataprovider.lesson.ILessonRepository;
import com.pucminas.bubble_app_back.dataprovider.lesson.IMissingStudentsRepository;
import com.pucminas.bubble_app_back.dataprovider.lesson.IStudentsAttendanceRepository;
import com.pucminas.bubble_app_back.dataprovider.solicitations.IReeschedulingSolicitationRepository;
import com.pucminas.bubble_app_back.dataprovider.user.IUserRepository;
import com.pucminas.bubble_app_back.entrypoint.lesson.dto.ClassCheckParams;
import com.pucminas.bubble_app_back.model.classchack.MissingStudents;
import com.pucminas.bubble_app_back.model.classchack.StudentsAttendance;
import com.pucminas.bubble_app_back.model.lesson.Lesson;
import com.pucminas.bubble_app_back.model.solicitations.ReeschedulingSolicitation;
import com.pucminas.bubble_app_back.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LessonService {
    @Autowired
    ILessonRepository aulaRepository;

    @Autowired
    IReeschedulingSolicitationRepository reeschedulingSolicitationRepository;

    @Autowired
    IStudentsAttendanceRepository studentsAttendanceRepository;

    @Autowired
    IMissingStudentsRepository missingStudentsRepository;

    @Autowired
    IUserRepository userRepository;

    public List<Lesson> saveLessons(List<Lesson> lessons) {
        return aulaRepository.saveAll(lessons);
    }

    public Lesson launchRescheduling(Long idReescheduling) {
        ReeschedulingSolicitation solicitation = reeschedulingSolicitationRepository.findById(idReescheduling).get();

        Lesson lessonParaRemarcar = solicitation.getRebookingLesson();

        lessonParaRemarcar.setStatus(LessonStatus.NOTDONE);
        lessonParaRemarcar.setLessonDate(solicitation.getNewLessonDate().withSecond(0).withNano(0));
        return aulaRepository.save(lessonParaRemarcar);
    }

    public List<Lesson> getLessonByBackage(Long aPackage){
        return aulaRepository.findByLessonPackagePackageId(aPackage);
    }

    public Lesson completedLesson(Long id) {
        Lesson lesson = aulaRepository.findById(id).get();
        lesson.setStatus(LessonStatus.DONE);
        return aulaRepository.save(lesson);
    }

    public void checkAttendance(ClassCheckParams params){
        List<User> students = userRepository.findAllById(params.getStudentsIds());
        Lesson lesson = aulaRepository.findById(params.getLessonId()).orElse(null);
        for (User student : students){
            studentsAttendanceRepository.save(StudentsAttendance.builder()
                            .student(student)
                            .lesson(lesson)
                    .build());
        }
    }

    public void checkMissing(ClassCheckParams params){
        List<User> students = userRepository.findAllById(params.getStudentsIds());
        Lesson lesson = aulaRepository.findById(params.getLessonId()).orElse(null);
        for (User student : students){
            missingStudentsRepository.save(MissingStudents.builder()
                    .student(student)
                    .lesson(lesson)
                    .build());
        }
    }
}