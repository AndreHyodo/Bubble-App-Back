package com.pucminas.bubble_app_back.entrypoint.lessonpackage.dto;

import com.pucminas.bubble_app_back.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackageDTO {
    private String packageName;
    private String packageDescription;
    private LocalDateTime initDate;
    private LocalDateTime endDate;
    private String emailProfessor;
    private List<String> emailAlunos;
    private LocalTime lessonTime;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private Integer lessonQuantity;
}
