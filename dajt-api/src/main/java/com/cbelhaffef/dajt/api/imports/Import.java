package com.cbelhaffef.dajt.api.imports;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="imports")
public class Import {

    @Id
    @GeneratedValue
    @Column(name="imp_id")
    private Long id;


    @Column(name="started")
    private Date started;

    @Column(name="ended")
    private Date ended;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private StatusImport status;

    @Column(name="message")
    private String message;

    @Column(name="file_name")
    private String fileName;

    @Column(name="file_size")
    private Long fileSize;

    @PrePersist
    public void prePersist() {
        started = new Date();
    }

}
