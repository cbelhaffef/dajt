package com.cbelhaffef.dajt.model.folder;

import com.cbelhaffef.dajt.enums.FolderPriority;
import com.cbelhaffef.dajt.enums.FolderStatus;
import com.cbelhaffef.dajt.enums.JudgementStatus;
import com.cbelhaffef.dajt.model.action.Action;
import com.cbelhaffef.dajt.model.advocate.Advocate;
import com.cbelhaffef.dajt.model.court.Court;
import com.cbelhaffef.dajt.model.guilty.Guilty;
import com.cbelhaffef.dajt.model.office.Office;
import com.cbelhaffef.dajt.model.user.User;
import com.cbelhaffef.dajt.model.victim.Victim;
import com.cbelhaffef.dajt.enums.JudgementStatus;
import com.cbelhaffef.dajt.model.action.Action;
import com.cbelhaffef.dajt.model.office.Office;
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

    @Column(name="number", nullable = false,unique = true)
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
    @JoinColumn(name="reporter", referencedColumnName = "user_id")
    private User reporter;

    @ManyToOne
    @JoinColumn(name="office_id" , nullable = false)
    private Office office;

    @Column(name="sending_type", nullable = false)
    private String sendingType;

    @Column(name="folder_status", nullable = false, columnDefinition = "varchar(50) default 'OPEN'")
    @Enumerated(EnumType.STRING)
    private FolderStatus status = FolderStatus.OPEN;

    @Column(name="folder_priority", nullable = false, columnDefinition = "varchar(50) default 'MINOR'")
    @Enumerated(EnumType.STRING)
    private FolderPriority priority = FolderPriority.MINOR;

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

    @ManyToMany
    @JoinTable(
        name = "folder_action",
        joinColumns = { @JoinColumn(name = "folder_id") },
        inverseJoinColumns = { @JoinColumn(name = "action_id") }
    )
    private Set<Action> actions = new HashSet<>();
}
