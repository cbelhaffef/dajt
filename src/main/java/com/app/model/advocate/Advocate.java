package com.app.model.advocate;

import com.app.model.affair.Affair;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="affair")
public class Advocate {

    @Id
    @Column(name="advocate_id")
    private Long id;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy="advocate")
    List<Affair> affairs;
}
