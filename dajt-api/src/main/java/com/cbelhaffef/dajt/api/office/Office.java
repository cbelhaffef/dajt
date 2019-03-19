package com.cbelhaffef.dajt.api.office;

import com.cbelhaffef.dajt.api.folder.Folder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="office")
public class Office {

    @Id
    @GeneratedValue
    @Column(name="office_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String code;

    @JsonIgnore
    @OneToMany(mappedBy="office")
    List<Folder> folders;
}
