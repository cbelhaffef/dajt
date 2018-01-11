package com.app.model.folder;

import com.app.enums.FolderStatus;
import com.app.enums.JudgementStatus;
import com.app.model.advocate.Advocate;
import com.app.model.court.Court;
import com.app.model.guilty.Guilty;
import com.app.model.user.User;
import com.app.model.victim.Victim;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name="last_modif_date")
    private Date lastModifDate;

    @Column(name="close_date")
    private Date closeDate;

    @Column(name="offence")
    private String offence;

    @Column(name="judgement_date")
    private Date judgementDate;

    @Column(name="judgement_status")
    @Enumerated(EnumType.STRING)
    private JudgementStatus judgementStatus;

    @ManyToOne
    @JoinColumn(name="court_id", nullable=true, updatable=true)
    private Court court;

    @ManyToOne
    @JoinColumn(name="advocate_id", nullable=true, updatable=true)
    private Advocate advocate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToMany
    @JoinTable(
        name = "folder_victim",
        joinColumns = { @JoinColumn(name = "folder_id") },
        inverseJoinColumns = { @JoinColumn(name = "victim_id") }
    )
    private Set<Victim> victims = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "folder_guilty",
        joinColumns = { @JoinColumn(name = "folder_id") },
        inverseJoinColumns = { @JoinColumn(name = "guilty_id") }
    )
    private Set<Guilty> guilties = new HashSet<>();

    public Folder(){}
}
