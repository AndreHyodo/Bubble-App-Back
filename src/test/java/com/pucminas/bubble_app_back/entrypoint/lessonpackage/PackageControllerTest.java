package com.pucminas.bubble_app_back.entrypoint.lessonpackage;

import com.pucminas.bubble_app_back.common.mapper.Mapper;
import com.pucminas.bubble_app_back.dataprovider.lessonpackage.IPackageRepository;
import com.pucminas.bubble_app_back.entrypoint.lessonpackage.dto.PackageDTO;
import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import com.pucminas.bubble_app_back.usecase.lessonpackage.GetPackageUseCase;
import com.pucminas.bubble_app_back.usecase.lessonpackage.SaveNewPackageUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PackageController.class)
public class PackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPackageRepository packageRepository;

    @MockBean
    private SaveNewPackageUseCase saveNewPackageUseCase;

    @MockBean
    private GetPackageUseCase getPackageUseCase;

    @MockBean
    private Mapper mapper;

    @BeforeEach
    public void setUp() {
        Package package1 = new Package();
        package1.setPackageId(1);
        package1.setLessonQuantity(10);
        package1.setInitDate(LocalDateTime.of(2024, 1, 1, 0, 0));

        Package package2 = new Package();
        package2.setPackageId(2);
        package2.setLessonQuantity(20);
        package2.setInitDate(LocalDateTime.of(2024, 2, 1, 0, 0));

        given(packageRepository.findById(1)).willReturn(Optional.of(package1));
        given(packageRepository.findById(2)).willReturn(Optional.empty());

        given(saveNewPackageUseCase.savenewPackage(any(PackageDTO.class))).willReturn(package1);
        given(getPackageUseCase.getStudentPackages(any(String.class))).willReturn(Arrays.asList(package1));
        given(getPackageUseCase.getTeacherPackages(any(String.class))).willReturn(Arrays.asList(package2));
    }

    @Test
    public void testUpdatePacote_NotFound() throws Exception {
        mockMvc.perform(put("/api/Pacote/updatePacote/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"lessonQuantity\": 15, \"initDate\": \"2024-01-01T00:00:00\" }"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePacote() throws Exception {
        given(packageRepository.existsById(1)).willReturn(true);

        mockMvc.perform(delete("/api/Pacote/deletePackage/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Pacote com ID 1 excluído com sucesso."));
    }

    @Test
    public void testDeletePacote_NotFound() throws Exception {
        given(packageRepository.existsById(2)).willReturn(false);

        mockMvc.perform(delete("/api/Pacote/deletePackage/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Pacote com ID 2 não encontrado."));
    }

    @Test
    public void testSavePacote() throws Exception {
        mockMvc.perform(post("/api/Pacote/savePackage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"lessonQuantity\": 10, \"initDate\": \"2024-01-01T00:00:00\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lessonQuantity").value(10));
    }

    @Test
    public void testGetPackageByStudentEmail() throws Exception {
        mockMvc.perform(get("/api/Pacote/getPackageByStudentEmail").param("studentEmail", "test@student.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lessonQuantity").value(10));
    }

    @Test
    public void testGetPackageByTeacherEmail() throws Exception {
        mockMvc.perform(get("/api/Pacote/getPackageByTeacherEmail").param("teacherEmail", "test@teacher.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lessonQuantity").value(20));
    }
}
