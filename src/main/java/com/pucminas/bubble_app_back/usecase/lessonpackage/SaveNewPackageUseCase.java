package com.pucminas.bubble_app_back.usecase.lessonpackage;

import com.pucminas.bubble_app_back.common.enums.LessonStatus;
import com.pucminas.bubble_app_back.common.mapper.Mapper;
import com.pucminas.bubble_app_back.core.chat.ChatService;
import com.pucminas.bubble_app_back.core.lesson.LessonService;
import com.pucminas.bubble_app_back.core.lessonpackage.PackageService;
import com.pucminas.bubble_app_back.core.studentpackage.StudentPackageService;
import com.pucminas.bubble_app_back.core.user.UserService;
import com.pucminas.bubble_app_back.entrypoint.lessonpackage.dto.PackageDTO;
import com.pucminas.bubble_app_back.model.lesson.Lesson;
import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import com.pucminas.bubble_app_back.model.studentpackage.StudentPackage;
import com.pucminas.bubble_app_back.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SaveNewPackageUseCase {
    @Autowired
    PackageService packageService;

    @Autowired
    UserService userService;

    @Autowired
    LessonService lessonService;

    @Autowired
    StudentPackageService studentPackageService;

    @Autowired
    ChatService chatService;

    @Autowired
    Mapper mapper;

    public Package savenewPackage(PackageDTO packageDTO) {
        Package packageTurma = mapper.generalMapper(packageDTO, Package.class);
        String emailProfessor = packageDTO.getEmailProfessor();
        List<String> emailAlunos = packageDTO.getEmailAlunos();

        List<User> alunos = userService.getAllUsersByEmail(emailAlunos);
        User professor = userService.getUserByEmail(emailProfessor);

        packageTurma.setTeacher(professor);
        Package novoPackage = packageService.salvaNovoPacote(packageTurma);

        LocalDateTime dataInicio = novoPackage.getInitDate();
        LocalDateTime dataFim = novoPackage.getEndDate();
        LocalTime lessonTime = packageDTO.getLessonTime();

        boolean monday = packageDTO.isMonday();
        boolean tuesday = packageDTO.isTuesday();
        boolean wednesday = packageDTO.isWednesday();
        boolean thursday = packageDTO.isThursday();
        boolean friday = packageDTO.isFriday();
        boolean saturday = packageDTO.isSaturday();

        List<Lesson> aulasDoPacote = new ArrayList<>();

        for (LocalDateTime data = dataInicio; aulasDoPacote.size() < packageDTO.getLessonQuantity(); data = data.plusDays(1)) {
            DayOfWeek diaSemana = data.getDayOfWeek();

            if ((monday && diaSemana == DayOfWeek.MONDAY) ||
                    (tuesday && diaSemana == DayOfWeek.TUESDAY) ||
                    (wednesday && diaSemana == DayOfWeek.WEDNESDAY) ||
                    (thursday && diaSemana == DayOfWeek.THURSDAY) ||
                    (friday && diaSemana == DayOfWeek.FRIDAY) ||
                    (saturday && diaSemana == DayOfWeek.SATURDAY)) {

                LocalDateTime dataComHora = data.withHour(lessonTime.getHour()).withMinute(lessonTime.getMinute());

                Lesson novaLesson = Lesson.builder()
                        .lessonDate(dataComHora)
                        .lessonPackage(novoPackage)
                        .status(LessonStatus.NOTDONE)
                        .build();
                aulasDoPacote.add(novaLesson);
            }
        }

        LocalDateTime dataUltimaAula = aulasDoPacote.isEmpty() ? null : aulasDoPacote.get(aulasDoPacote.size() - 1).getLessonDate();


        lessonService.saveLessons(aulasDoPacote);

        novoPackage.setLessonQuantity(packageDTO.getLessonQuantity());
        novoPackage.setEndDate(dataUltimaAula);
        packageService.salvaNovoPacote(novoPackage);

        List<StudentPackage> studentPackages = new ArrayList<>();
        for (User aluno : alunos) {
            studentPackages.add(StudentPackage.builder()
                    .student(aluno)
                    .studentPackage(novoPackage)
                    .build());
        }

        List<StudentPackage> studentPackagesSaved = studentPackageService.saveAll(studentPackages);

        novoPackage.setStudentPackages(studentPackagesSaved);

        for (User aluno : alunos) {
            chatService.createChatRoom(aluno, professor);
        }

        return novoPackage;
    }
}
