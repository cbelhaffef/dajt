package com.cbelhaffef.dajt.model.guilty;

import com.cbelhaffef.dajt.model.folder.Folder;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name="name")
    private String name;
}
