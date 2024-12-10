package com.pucminas.bubble_app_back.model.classchack;

import com.pucminas.bubble_app_back.model.lesson.Lesson;
import com.pucminas.bubble_app_back.model.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class StudentsAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "lesson_lesson_id")
    Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "student_id")
    User student;
}
