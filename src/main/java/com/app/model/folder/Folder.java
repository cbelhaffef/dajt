package com.app.model.folder;

import com.app.enums.FolderStatus;
import com.app.model.affair.Affair;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="folder")
public class Folder {

    @Id
    @Column(name="folder_id")
    private Long id;
    private String number;

    @Column(name="folder_status")
    private FolderStatus Status;

    @Column(name="create_dth")
    private Date creationDate;

    @Column(name="modif_dth")
    private Date modificationDate;

    @Column(name="close_dth")
    private Date closeDate;

    @OneToMany(mappedBy="folder")
    private List<Affair> affairs;
}
