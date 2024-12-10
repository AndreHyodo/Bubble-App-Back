package com.pucminas.bubble_app_back.core.solicitations;

import com.pucminas.bubble_app_back.common.enums.LessonStatus;
import com.pucminas.bubble_app_back.common.enums.ReeschedulingStatus;
import com.pucminas.bubble_app_back.core.lesson.LessonService;
import com.pucminas.bubble_app_back.core.solicitations.dto.ReeschedulingSolicitationParams;
import com.pucminas.bubble_app_back.dataprovider.lesson.ILessonRepository;
import com.pucminas.bubble_app_back.dataprovider.solicitations.IReeschedulingSolicitationRepository;
import com.pucminas.bubble_app_back.dataprovider.user.IUserRepository;
import com.pucminas.bubble_app_back.entrypoint.lesson.dto.ResponseSolicitationDTO;
import com.pucminas.bubble_app_back.model.lesson.Lesson;
import com.pucminas.bubble_app_back.model.solicitations.ReeschedulingSolicitation;
import com.pucminas.bubble_app_back.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReeschedulingSolicitationService {
    @Autowired
    IReeschedulingSolicitationRepository repositorio;

    @Autowired
    LessonService lessonService;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ILessonRepository lessonRepository;

    public ReeschedulingSolicitation launchSolicitation(ReeschedulingSolicitationParams params){
        User aluno = userRepository.findByEmail(params.getEmailAluno());
        String message = params.getMessage();
        Lesson lesson = lessonRepository.findById(params.getLessonId()).orElse(null);
        lesson.setStatus(LessonStatus.RESCHEDULING);
        lessonRepository.save(lesson);
        User teacher = lesson.getLessonPackage().getTeacher();

        if(lesson == null){
            return null;
        }
        return repositorio.save(ReeschedulingSolicitation.builder()
                .newLessonDate(params.getNewLessonDate())
                .reeschedulingStatus(ReeschedulingStatus.ANALYSING)
                .student(aluno)
                .rebookingLesson(lesson)
                .message(message)
                .messageDate(LocalDateTime.now())
                .teacher(teacher)
                .build());
    }

    public ReeschedulingSolicitation aceptSolicitation(ResponseSolicitationDTO responseSolicitationDTO){
        ReeschedulingSolicitation reeschedulingSolicitation = repositorio.findById(responseSolicitationDTO.getIdReeschedulingSolicitation()).orElse(null);

        if(reeschedulingSolicitation == null){
            return null;
        }

        reeschedulingSolicitation.setReeschedulingStatus(ReeschedulingStatus.APROVED);

        lessonService.launchRescheduling(reeschedulingSolicitation.getId());

        if (responseSolicitationDTO.getHasResponseMessage()){
            reeschedulingSolicitation.setResponseMessage(responseSolicitationDTO.getResponseMessage());
        }

        return repositorio.save(reeschedulingSolicitation);
    }

    public ReeschedulingSolicitation rejectSolicitation(ResponseSolicitationDTO responseSolicitationDTO){
        ReeschedulingSolicitation reeschedulingSolicitation = repositorio.findById(responseSolicitationDTO.getIdReeschedulingSolicitation()).orElse(null);
        Lesson lesson = lessonRepository.findById(reeschedulingSolicitation.getRebookingLesson().getLessonId()).orElse(null);
        lesson.setStatus(LessonStatus.NOTDONE);
        lessonRepository.save(lesson);
        if(reeschedulingSolicitation == null){
            return null;
        }

        reeschedulingSolicitation.setReeschedulingStatus(ReeschedulingStatus.REJECTED);

        if (responseSolicitationDTO.getHasResponseMessage()){
            reeschedulingSolicitation.setResponseMessage(responseSolicitationDTO.getResponseMessage());
        }

        return repositorio.save(reeschedulingSolicitation);
    }

    public List<ReeschedulingSolicitation> getSolicitationsByStudent(Long studentId) {
        return repositorio.findByStudentId(studentId);
    }

    public List<ReeschedulingSolicitation> getSolicitationsByTeacher(Long teacherId) {
        return repositorio.findByRebookingLesson_LessonPackage_TeacherId(teacherId);
    }
}
