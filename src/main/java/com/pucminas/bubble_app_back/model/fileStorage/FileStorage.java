package com.pucminas.bubble_app_back.model.fileStorage;

import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "materiais")
@Getter
@Setter
@NoArgsConstructor
@Data
public class FileStorage {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;
    private String filetype;

    @ManyToOne
    @JoinColumn(name = "material_package_package_id")
    private Package materialPackage;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    public FileStorage(String fileName, String filetype, byte[] data) {
        this.fileName = fileName;
        this.filetype = filetype;
        this.data = data;
    }
}
