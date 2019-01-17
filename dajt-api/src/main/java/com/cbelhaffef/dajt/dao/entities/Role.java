package com.cbelhaffef.dajt.dao.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue
    @Column(name="role_id")
    private Long id;

    @Column(name="name")
    private String name;

}
