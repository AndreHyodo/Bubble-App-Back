package com.pucminas.bubble_app_back.usecase.lesson;

import com.pucminas.bubble_app_back.common.enums.LessonStatus;
import com.pucminas.bubble_app_back.core.lesson.LessonService;
import com.pucminas.bubble_app_back.dataprovider.lesson.ILessonRepository;
import com.pucminas.bubble_app_back.dataprovider.lesson.IMissingStudentsRepository;
import com.pucminas.bubble_app_back.dataprovider.lesson.IStudentsAttendanceRepository;
import com.pucminas.bubble_app_back.dataprovider.lessonpackage.IPackageRepository;
import com.pucminas.bubble_app_back.entrypoint.lesson.dto.LessonDTO;
import com.pucminas.bubble_app_back.entrypoint.lesson.dto.ResponseClassCheckDTO;
import com.pucminas.bubble_app_back.model.classchack.MissingStudents;
import com.pucminas.bubble_app_back.model.classchack.StudentsAttendance;
import com.pucminas.bubble_app_back.model.lesson.Lesson;
import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import com.pucminas.bubble_app_back.model.studentpackage.StudentPackage;
import com.pucminas.bubble_app_back.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetLessonsUseCase {

    @Autowired
    LessonService lessonService;

    @Autowired
    ILessonRepository lessonRepository;

    @Autowired
    IPackageRepository packageRepository;

    @Autowired
    IMissingStudentsRepository missingStudentsRepository;

    @Autowired
    IStudentsAttendanceRepository studentsAttendanceRepository;

    public Lesson remarcarAula(Long idReescheduling){
        return lessonService.launchRescheduling(idReescheduling);
    }

    public List<LessonDTO> getPackageLessons(Integer idPackage){
        List<Lesson> lessons = lessonService.getLessonByBackage(Long.valueOf(idPackage));

        List<Long> lessonIds = lessons.stream()
                .map(Lesson::getLessonId)
                .toList();

        List<MissingStudents> missingStudents = missingStudentsRepository.findByLessonIds(lessonIds);
        List<StudentsAttendance> studentsAtendance = studentsAttendanceRepository.findByLessonIds(lessonIds);

        List<LessonDTO> lessonDTOS = new ArrayList<>();

        Package pacote = packageRepository.findById(idPackage).orElse(null);

        List<StudentPackage> studentPackages = pacote.getStudentPackages();
        List<User> students = studentPackages.stream().map(StudentPackage::getStudent).toList();

        for (Lesson lesson : lessons) {
            missingStudents = missingStudents.stream()
                            .filter(x -> x.getLesson().getLessonId().equals(lesson.getLessonId())).toList();

            studentsAtendance = studentsAtendance.stream()
                    .filter(x -> x.getLesson().getLessonId().equals(lesson.getLessonId())).toList();

            lessonDTOS.add(LessonDTO.builder()
                            .status(lesson.getStatus())
                            .lessonId(lesson.getLessonId())
                            .lessonDate(lesson.getLessonDate())
                            .students(students)
                            .lessonPackageId(Long.valueOf(lesson.getLessonPackage().getPackageId()))
                            .ausStudentsIds(missingStudents.stream().map(x -> x.getStudent().getId()).collect(Collectors.toList()))
                            .presentStudentsIds(studentsAtendance.stream().map(x -> x.getStudent().getId()).collect(Collectors.toList()))
                    .build());
        }

        return lessonDTOS;
    }

    public Lesson completeLesson(Long idLesson){
        Lesson lesson = lessonService.completedLesson(idLesson);
        updateCompletedPercentage(lesson.getLessonPackage().getPackageId());
        return lesson;
    }

    public void updateCompletedPercentage(Integer packageId) {
        Package pacote = packageRepository.findById(packageId).orElseThrow(
                () -> new RuntimeException("Pacote não encontrado para o ID: " + packageId)
        );

        List<Lesson> lessons = lessonService.getLessonByBackage(Long.valueOf(packageId));

        long totalLessons = lessons.size();
        long completedLessons = lessons.stream()
                .filter(lesson -> LessonStatus.DONE.equals(lesson.getStatus()))
                .count();

        double completedPercentage = (totalLessons == 0) ? 0 : (double) completedLessons / totalLessons * 100;

        pacote.setCompletedPercentage(completedPercentage);

        packageRepository.save(pacote);
    }

    public ResponseClassCheckDTO getResponseClassCheck(Long idLesson){
        Lesson lesson = lessonRepository.findById(idLesson).orElse(null);

        List<Long> studentsAttendanceIds = studentsAttendanceRepository.findByLesson(lesson).stream()
                .map(x -> x.getStudent().getId()).toList();

        List<Long> missingStudentsIds = missingStudentsRepository.findByLesson(lesson).stream()
                .map(x -> x.getStudent().getId()).toList();

        return ResponseClassCheckDTO.builder()
                .studentsAttendance(studentsAttendanceIds)
                .missingStudents(missingStudentsIds)
                .build();

    }

}
