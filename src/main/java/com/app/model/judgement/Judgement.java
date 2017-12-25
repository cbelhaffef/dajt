package com.app.model.judgement;

import com.app.model.affair.Affair;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="judgement")
public class Judgement {

    @Id
    @Column(name="judg_id")
    private Long id;
    private String number;
    private Date judgementDate;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Affair affair;

}
