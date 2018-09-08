package com.cbelhaffef.dajt.model.victim;

import com.cbelhaffef.dajt.model.folder.Folder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name="victim")
public class Victim {

    @Id
    @GeneratedValue
    @Column(name="victim_id")
    private Long id;

    @Column(name="name")
    private String name;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="folder_id")
    private Folder folder;

    public Victim() {
        super();
    }

    public Victim(String name,Folder folder) {
        this.name = name;
        this.folder = folder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Victim victim = (Victim) o;
        return Objects.equals(name, victim.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
