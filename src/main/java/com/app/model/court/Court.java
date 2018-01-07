package com.app.model.court;

import com.app.model.folder.Folder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="court")
public class Court {

    @Id
    @GeneratedValue
    @Column(name="court_id")
    private Long id;

    @Column(name="name")
    private String firstName;

    @OneToMany(mappedBy="court")
    List<Folder> folders;
}
