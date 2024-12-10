package com.pucminas.bubble_app_back.entrypoint.lesson;

import com.pucminas.bubble_app_back.core.lesson.LessonService;
import com.pucminas.bubble_app_back.core.solicitations.ReeschedulingSolicitationService;
import com.pucminas.bubble_app_back.core.solicitations.dto.ReeschedulingSolicitationParams;
import com.pucminas.bubble_app_back.entrypoint.lesson.dto.ClassCheckParams;
import com.pucminas.bubble_app_back.entrypoint.lesson.dto.LessonDTO;
import com.pucminas.bubble_app_back.entrypoint.lesson.dto.ResponseClassCheckDTO;
import com.pucminas.bubble_app_back.entrypoint.lesson.dto.ResponseSolicitationDTO;
import com.pucminas.bubble_app_back.entrypoint.lessonpackage.dto.LessonFormatDTO;
import com.pucminas.bubble_app_back.model.lesson.Lesson;
import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import com.pucminas.bubble_app_back.model.solicitations.ReeschedulingSolicitation;
import com.pucminas.bubble_app_back.usecase.lesson.GetLessonsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/Lesson")
public class LessonController {
    @Autowired
    LessonService lessonService;

    @Autowired
    GetLessonsUseCase getLessonsUseCase;

    @Autowired
    ReeschedulingSolicitationService reeschedulingSolicitationService;

    @PostMapping("/sendReeschedulingSolicitattion")
    public ResponseEntity<ReeschedulingSolicitation> send(@RequestBody ReeschedulingSolicitationParams params){
        return ResponseEntity.ok(reeschedulingSolicitationService.launchSolicitation(params));
    }

    @GetMapping("/getLessonsByPackage")
    public ResponseEntity<List<LessonDTO>> getStudentsClass(@RequestParam Integer idPackage) {
        return ResponseEntity.ok(getLessonsUseCase.getPackageLessons(idPackage));
    }

    @PutMapping("/launchReescheduling")
    public ResponseEntity<Lesson> launchReescheduling(@RequestParam Long idReescheduling) {
        return ResponseEntity.ok(getLessonsUseCase.remarcarAula(idReescheduling));
    }

    @PutMapping("/completeLesson")
    public ResponseEntity<Lesson> completeLesson(@RequestParam Long idLesson) {
        return ResponseEntity.ok(getLessonsUseCase.completeLesson(idLesson));
    }

    @PutMapping("/aceptReeschedulingSolicitation")
    public ResponseEntity<ReeschedulingSolicitation> acept(@RequestBody ResponseSolicitationDTO responseSolicitationDTO) {
        return ResponseEntity.ok(reeschedulingSolicitationService.aceptSolicitation(responseSolicitationDTO));
    }

    @PutMapping("/rejectReeschedulingSolicitation")
    public ResponseEntity<ReeschedulingSolicitation> reject(@RequestBody ResponseSolicitationDTO responseSolicitationDTO) {
        return ResponseEntity.ok(reeschedulingSolicitationService.rejectSolicitation(responseSolicitationDTO));
    }   

    @PutMapping("/attendance")
    public ResponseEntity<HttpStatus> attendance(@RequestBody ClassCheckParams params) {
        lessonService.checkAttendance(params);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/missing")
    public ResponseEntity<HttpStatus> missing(@RequestBody ClassCheckParams params) {
        lessonService.checkMissing(params);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/getSolicitationsByStudent")
    public ResponseEntity<List<ReeschedulingSolicitation>> getSolicitationsByStudent(@RequestParam Long studentId) {
        List<ReeschedulingSolicitation> solicitations = reeschedulingSolicitationService.getSolicitationsByStudent(studentId);
        return ResponseEntity.ok(solicitations);
    }

    @GetMapping("/getSolicitationsByTeacher")
    public ResponseEntity<List<ReeschedulingSolicitation>> getSolicitationsByTeacher(@RequestParam Long teacherId) {
        List<ReeschedulingSolicitation> solicitations = reeschedulingSolicitationService.getSolicitationsByTeacher(teacherId);
        return ResponseEntity.ok(solicitations);
    }

    @GetMapping("/getAttendanceByLesson")
    public ResponseEntity<ResponseClassCheckDTO> getClassCheck(@RequestParam Long lessonId){
        return ResponseEntity.ok(getLessonsUseCase.getResponseClassCheck(lessonId));
    }
}
