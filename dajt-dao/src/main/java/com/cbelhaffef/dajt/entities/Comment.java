package com.cbelhaffef.dajt.model.comment;

import com.cbelhaffef.dajt.model.folder.Folder;
import com.cbelhaffef.dajt.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name="comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="author", referencedColumnName = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name="author_updated", referencedColumnName = "user_id")
    private User authorUpdated;

    @Column(name="body")
    private String body;


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
