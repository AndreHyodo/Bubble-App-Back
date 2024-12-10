package com.pucminas.bubble_app_back.model.lesson;

import com.pucminas.bubble_app_back.common.enums.LessonStatus;
import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import com.pucminas.bubble_app_back.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Entity(name = "Aulas")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long lessonId;

    LocalDateTime lessonDate;

    @ManyToOne
    @JoinColumn(name = "pacote_da_aula_id_pacote")
    Package lessonPackage;

    LessonStatus status = LessonStatus.NOTDONE;

}
