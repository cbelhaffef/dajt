package com.cbelhaffef.dajt.model.priority;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="priority")
public class Priority {

    @Id
    @GeneratedValue
    @Column(name="priority_id")
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
