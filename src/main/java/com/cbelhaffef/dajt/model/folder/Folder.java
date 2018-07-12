package com.cbelhaffef.dajt.model.folder;

import com.cbelhaffef.dajt.enums.FolderPriority;
import com.cbelhaffef.dajt.enums.FolderStatus;
import com.cbelhaffef.dajt.enums.JudgementStatus;
import com.cbelhaffef.dajt.model.accused.Accused;
import com.cbelhaffef.dajt.model.action.Action;
import com.cbelhaffef.dajt.model.actionlog.ActionLog;
import com.cbelhaffef.dajt.model.advocate.Advocate;
import com.cbelhaffef.dajt.model.comment.Comment;
import com.cbelhaffef.dajt.model.court.Court;
import com.cbelhaffef.dajt.model.office.Office;
import com.cbelhaffef.dajt.model.user.User;
import com.cbelhaffef.dajt.model.victim.Victim;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name="folder")
public class Folder implements Comparable<Folder>{

    @Id
    @GeneratedValue
    @Column(name="folder_id")
    private Long id;

    @Column(name="number", nullable = false, unique = true)
    private String number;

    @Column(name="directorate_number")
    private String directorateNumber;

    @Column(name="offence")
    private String offence;

    @Column(name="offence_date")
    private Date offenceDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="court_id")
    private Court court;

    @ManyToOne
    @JoinColumn(name="assignee", referencedColumnName = "user_id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name="reporter", referencedColumnName = "user_id")
    private User reporter;

    @ManyToOne
    @JoinColumn(name="office_id")
    private Office office;

    @Column(name="administration_concerned")
    private String administrationConcerned;

    @Column(name="folder_status", nullable = false, columnDefinition = "varchar(50) default 'OPEN'")
    @Enumerated(EnumType.STRING)
    private FolderStatus status = FolderStatus.OPEN;

    @Column(name="folder_priority", nullable = false, columnDefinition = "varchar(50) default 'MINOR'")
    @Enumerated(EnumType.STRING)
    private FolderPriority priority = FolderPriority.MINOR;

    @CreationTimestamp
    @Column(name="created")
    private Date created;

    @UpdateTimestamp
    @Column(name="updated")
    private Date updated;

    @Column(name="closed")
    private Date closed;

    @Column(name="judged")
    private Date judged;

    @Column(name="judgement_status")
    @Enumerated(EnumType.STRING)
    private JudgementStatus judgementStatus;

    @ManyToOne
    @JoinColumn(name="advocate_id")
    private Advocate advocate;

    @OneToMany(mappedBy="folder",cascade = CascadeType.PERSIST)
    private Set<Victim> victims = new HashSet<>();

    @OneToMany(mappedBy="folder",cascade = CascadeType.PERSIST)
    private Set<Accused> accused = new HashSet<>();

    @OneToMany(mappedBy="folder")
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy="folder")
    private Set<ActionLog> actionLogs = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "folder_action",
        joinColumns = { @JoinColumn(name = "folder_id") },
        inverseJoinColumns = { @JoinColumn(name = "action_id") }
    )
    private Set<Action> actions = new HashSet<>();

    @Override
    public int compareTo(@NotNull Folder o) {
        return this.getNumber().compareTo(o.getNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Folder folder = (Folder) o;
        return Objects.equals(id, folder.id) &&
            Objects.equals(number, folder.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, number);
    }
}
