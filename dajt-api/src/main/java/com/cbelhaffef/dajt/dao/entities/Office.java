package com.cbelhaffef.dajt.dao.entities;

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

    @Column(name="name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy="office")
    List<Folder> folders;
}
