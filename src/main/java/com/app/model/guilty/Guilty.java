package com.app.model.guilty;

import com.app.model.folder.Folder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;


import javax.persistence.*;
import java.util.HashSet;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="guilty")
public class Guilty {

    @Id
    @GeneratedValue
    @Column(name="guilty_id")
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @ManyToMany(mappedBy = "guilties")
    private Set<Folder> folders = new HashSet<>();
}
