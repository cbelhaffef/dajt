package com.app.model.advocate;

import com.app.model.folder.Folder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="advocate")
public class Advocate {

    @Id
    @GeneratedValue
    @Column(name="advocate_id")
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @JsonIgnore
    @OneToMany(mappedBy="advocate")
    List<Folder> folders;
}
