package com.pucminas.bubble_app_back.core.solicitations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReeschedulingSolicitationParams {
    private Long lessonId;
    private String emailAluno;
    private LocalDateTime newLessonDate;
    private String message;
}
