package com.pucminas.bubble_app_back.entrypoint.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSolicitationDTO {
    Long idReeschedulingSolicitation;
    String responseMessage;
    Boolean hasResponseMessage;
}
