package com.cbelhaffef.dajt.api.transmission;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="transmission")
public class Transmission {

    @Id
    @GeneratedValue
    @Column(name="transmission")
    private Long id;

    @Column(name="amount")
    private Long amount;

    @Column(name="work_done")
    private String workDone;

    @Column(name="note")
    private String note;

    @Column(name="send_number")
    private Long sendNumber;

    @Column(name="send_date")
    private Date sendDate;

    @Column(name="transmitted", columnDefinition = "boolean default false", nullable = false)
    private Boolean transmitted = false;
}
