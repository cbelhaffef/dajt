package com.cbelhaffef.dajt.api.role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NaturalId;

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

    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleCodeEnum code;

}
