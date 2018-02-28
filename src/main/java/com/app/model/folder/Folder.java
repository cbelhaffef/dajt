package com.app.model.folder;

import com.app.enums.FolderStatus;
import com.app.enums.JudgementStatus;
import com.app.model.advocate.Advocate;
import com.app.model.court.Court;
import com.app.model.guilty.Guilty;
import com.app.model.office.Office;
import com.app.model.user.User;
import com.app.model.victim.Victim;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name="number", nullable = false)
    private String number;

    @Column(name="offence", nullable = false)
    private String offence;

    @ManyToOne
    @JoinColumn(name="court_id")
    private Court court;

    @ManyToOne
    @JoinColumn(name="assignee", referencedColumnName = "user_id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name="office_id" , nullable = false)
    private Office office;

    @Column(name="sending_type", nullable = false)
    private String sendingType;

    @Column(name="folder_status", nullable = false, columnDefinition = "varchar(50) default 'OPEN'")
    @Enumerated(EnumType.STRING)
    private FolderStatus status;

    @CreationTimestamp
    @Column(name="create_date")
    private Date createDate;

    @UpdateTimestamp
    @Column(name="modif_date")
    private Date modifDate;

    @Column(name="close_date")
    private Date closeDate;

    @Column(name="judgement_date")
    private Date judgementDate;

    @Column(name="judgement_status")
    @Enumerated(EnumType.STRING)
    private JudgementStatus judgementStatus;

    @ManyToOne
    @JoinColumn(name="advocate_id")
    private Advocate advocate;

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
}
