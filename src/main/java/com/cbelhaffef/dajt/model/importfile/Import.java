package com.cbelhaffef.dajt.model.importfile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="import")
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
    private ImportStatusEnum status;

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
