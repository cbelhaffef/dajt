package com.app.model.folder;

import com.app.model.affair.Affair;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="folder")
public class Folder {

    @Id
    @Column(name="folder_id")
    private Long id;
    private String number;

    @OneToMany(mappedBy="folder")
    private List<Affair> affairs;

}
