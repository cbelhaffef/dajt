package com.app.model.judgement;

import com.app.enums.JudgementStatus;
import com.app.model.folder.Folder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="judgement")
public class Judgement {

    @Id
    @GeneratedValue
    @Column(name="judgement_id")
    private Long id;

    @Column(name="number")
    private String number;

    @Column(name="judgement_date")
    private Date judgementDate;

    @Column(name="judgement_status")
    @Enumerated(EnumType.STRING)
    private JudgementStatus status;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Folder folder;
}
