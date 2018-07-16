package com.cbelhaffef.dajt.model.advocate;

import com.cbelhaffef.dajt.model.folder.Folder;
import com.cbelhaffef.dajt.model.folder.Folder;
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

    @Column(name="firstname")
    private String firstname;

    @Column(name="lastname")
    private String lastname;

    @JsonIgnore
    @OneToMany(mappedBy="advocate")
    List<Folder> folders;
}
