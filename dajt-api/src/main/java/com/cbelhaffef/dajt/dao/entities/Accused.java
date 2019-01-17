package com.cbelhaffef.dajt.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="accused")
public class Accused {

    @Id
    @GeneratedValue
    @Column(name="accused_id")
    private Long id;

    @Column(name="name")
    private String name;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="folder_id")
    private Folder folder;

    public Accused() {
        super();
    }

    public Accused(String name, Folder folder) {
        this.name = name;
        this.folder = folder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Accused accused = (Accused) o;
        return Objects.equals(id, accused.id) &&
            Objects.equals(name, accused.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name);
    }
}
