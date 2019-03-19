package com.cbelhaffef.dajt.api.court;

import com.cbelhaffef.dajt.api.folder.Folder;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name="name", unique = true, nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy="court")
    List<Folder> folders;

    public Court() {
        super();
    }

    public Court(String name) {
        this.name = name;
    }
}
