package com.cbelhaffef.dajt.dao.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="status")
public class Status {

    @Id
    @GeneratedValue
    @Column(name="status_id")
    private Long id;

    @Column(name="name", unique = true, nullable = false)
    private String name;

    @Column(name="code", unique = true, nullable = false)
    private String code;

    @Column(name="icon")
    private String icon;

    @Column(name="description")
    private String description;

}
