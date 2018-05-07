package com.cbelhaffef.dajt.model.victim;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="victim")
public class Victim {

    @Id
    @GeneratedValue
    @Column(name="victim_id")
    private Long id;

    @Column(name="name")
    private String name;
}
