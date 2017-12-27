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
    @Column(name="id")
    private Long id;
    private String number;

    @Column(name="status")
    private FolderStatus status;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="modif_date")
    private Date modifDate;

    @Column(name="close_date")
    private Date closeDate;

    @OneToMany(mappedBy="folder")
    private List<Affair> affairs;
}
