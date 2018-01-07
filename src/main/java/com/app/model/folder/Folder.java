package com.app.model.folder;

import com.app.enums.FolderStatus;
import com.app.enums.FolderTopic;
import com.app.model.advocate.Advocate;
import com.app.model.court.Court;
import com.app.model.judgement.Judgement;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="folder")
public class Folder {

    @Id
    @GeneratedValue
    @Column(name="folder_id")
    private Long id;

    @Column(name="number")
    private String number;

    @Column(name="folder_status")
    @Enumerated(EnumType.STRING)
    private FolderStatus status;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="modif_date")
    private Date modifDate;

    @Column(name="close_date")
    private Date closeDate;

    @Column(name="offence")
    private String offence;

    @ManyToOne
    @JoinColumn(name="court__id", nullable=true, updatable=true)
    private Court court;

    @ManyToOne
    @JoinColumn(name="advocate_id", nullable=true, updatable=true)
    private Advocate advocate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "affair", cascade = CascadeType.ALL)
    @JoinColumn(name = "judgement_id")
    private Judgement judgement;

    public Folder(){}
}
