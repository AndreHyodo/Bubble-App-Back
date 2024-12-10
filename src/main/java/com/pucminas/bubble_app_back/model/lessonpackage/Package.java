package com.pucminas.bubble_app_back.model.lessonpackage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pucminas.bubble_app_back.model.studentpackage.StudentPackage;
import com.pucminas.bubble_app_back.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Data
@Setter
@Entity(name = "Pacotes")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer packageId;

    String packageName;

    String packageDescription;

    Integer lessonQuantity;

    LocalDateTime initDate;

    LocalDateTime endDate;

    Double completedPercentage = 0.0;

    Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "professor_email")
    User teacher;

    @OneToMany(mappedBy = "studentPackage")
    @JsonManagedReference
    private List<StudentPackage> studentPackages;
}
