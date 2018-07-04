package com.cbelhaffef.dajt.model.importfile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;


import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name="import")
public class Import {

    @Id
    @GeneratedValue
    @Column(name="imp_id")
    private Long id;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;

    @Column(name="imp_process_date")
    private Date processDate;

    @Column(name="imp_status")
    @Enumerated(EnumType.STRING)
    private ImportStatusEnum status;

    @Column(name="imp_message")
    private String message;

    @Column(name="imp_file_name")
    private String fileName;

    @Column(name="imp_file_size")
    private Long fileSize;
}
