package com.pucminas.bubble_app_back.entrypoint.lesson.dto;

import com.pucminas.bubble_app_back.common.enums.LessonStatus;
import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import com.pucminas.bubble_app_back.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    private Long lessonId;

    private LocalDateTime lessonDate;

    private Long lessonPackageId;

    private List<User> students;

    private List<Long> presentStudentsIds;

    private List<Long> ausStudentsIds;

    private LessonStatus status;
}
