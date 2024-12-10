package com.pucminas.bubble_app_back.model.solicitations;

import com.pucminas.bubble_app_back.common.enums.ReeschedulingStatus;
import com.pucminas.bubble_app_back.model.lesson.Lesson;
import com.pucminas.bubble_app_back.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "SolicitacoesRemarcacao")
public class ReeschedulingSolicitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    ReeschedulingStatus reeschedulingStatus;

    @ManyToOne
    @JoinColumn(name = "student_id")
    User student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    User teacher;

    @ManyToOne
    @JoinColumn(name = "rebooking_lesson_lesson_id")
    Lesson rebookingLesson;

    String message;

    LocalDateTime newLessonDate;

    LocalDateTime messageDate;

    String responseMessage;
}
