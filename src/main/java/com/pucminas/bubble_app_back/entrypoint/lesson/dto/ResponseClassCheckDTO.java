package com.pucminas.bubble_app_back.entrypoint.lesson.dto;

import com.pucminas.bubble_app_back.model.classchack.MissingStudents;
import com.pucminas.bubble_app_back.model.classchack.StudentsAttendance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseClassCheckDTO {
    private List<Long> missingStudents;
    private List<Long> studentsAttendance;
}
