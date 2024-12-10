package com.pucminas.bubble_app_back.entrypoint.lessonpackage;

import com.pucminas.bubble_app_back.common.mapper.Mapper;
import com.pucminas.bubble_app_back.dataprovider.lessonpackage.IPackageRepository;
import com.pucminas.bubble_app_back.entrypoint.lessonpackage.dto.PackageDTO;
import com.pucminas.bubble_app_back.entrypoint.lessonpackage.dto.RequestNewLessonPackageDTO;
import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import com.pucminas.bubble_app_back.usecase.lessonpackage.GetPackageUseCase;
import com.pucminas.bubble_app_back.usecase.lessonpackage.SaveNewPackageUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Pacote")
public class PackageController {

    @Autowired
    private IPackageRepository IPackageRepository;

    @Autowired
    SaveNewPackageUseCase saveNewPackageUseCase;

    @Autowired
    GetPackageUseCase getPackageUseCase;

    @Autowired
    Mapper mapper;

    @PutMapping("/updatePacote/{id}")
    public ResponseEntity<Package> updatePacote(@PathVariable Integer id, @RequestBody Package packageAtualizado) {
        Optional<Package> pacoteExistente = IPackageRepository.findById(id);

        if (pacoteExistente.isPresent()) {
            Package aPackage = pacoteExistente.get();
            aPackage.setLessonQuantity(packageAtualizado.getLessonQuantity());
            aPackage.setInitDate(packageAtualizado.getInitDate());
            return new ResponseEntity<>(IPackageRepository.save(aPackage), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletePackage/{id}")
    public ResponseEntity<String> deletePacote(@PathVariable Integer id) {
        if (IPackageRepository.existsById(id)) {
            Package pacote = IPackageRepository.findById(id).orElse(null);
            pacote.setIsActive(false);
            IPackageRepository.save(pacote);
            return ResponseEntity.ok("Pacote com ID " + id + " excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pacote com ID " + id + " não encontrado.");
        }
    }

    @PostMapping("/savePackage")
    public ResponseEntity<Package> salvaPacote(@RequestBody PackageDTO params) {
        return ResponseEntity.ok(saveNewPackageUseCase.savenewPackage(params));
    }

    @GetMapping("/getPackageByStudentEmail")
    public ResponseEntity<List<Package>> obterPacotePorAluno(String studentEmail) {
        return ResponseEntity.ok(getPackageUseCase.getStudentPackages(studentEmail));
    }

    @GetMapping("/getPackageByTeacherEmail")
    public ResponseEntity<List<Package>> obterPacotePorProfessor(String teacherEmail) {
        return ResponseEntity.ok(getPackageUseCase.getTeacherPackages(teacherEmail));
    }
}