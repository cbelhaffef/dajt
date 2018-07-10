package com.cbelhaffef.dajt.model.actionlog;

import com.cbelhaffef.dajt.model.action.Action;
import com.cbelhaffef.dajt.model.folder.Folder;
import com.cbelhaffef.dajt.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="actionlog")
public class ActionLog {

    @Id
    @GeneratedValue
    @Column(name="actionlog_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="author", referencedColumnName = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name="author_updated", referencedColumnName = "user_id")
    private User authorUpdated;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Action action;

    @Column(name="created")
    private Date created;

    @Column(name="updated")
    private Date updated;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="folder_id")
    private Folder folder;

    @PrePersist
    public void prePersist() {
        created = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updated = new Date();
    }

}
