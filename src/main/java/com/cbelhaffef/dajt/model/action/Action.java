package com.cbelhaffef.dajt.model.action;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="action")
public class Action {

    @Id
    @GeneratedValue
    @Column(name="action_id")
    private Long id;

    @Column(name="name")
    private String name;

}
