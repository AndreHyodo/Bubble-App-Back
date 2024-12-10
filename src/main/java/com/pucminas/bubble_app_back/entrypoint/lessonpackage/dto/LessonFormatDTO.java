package com.pucminas.bubble_app_back.entrypoint.lessonpackage.dto;

import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonFormatDTO {
    Long lessonId;
    LocalDateTime lessonDate;
    Package aPackage;
    String teacherEmail;
    List<String> studentEmails;
}
