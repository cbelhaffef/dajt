package com.app.model.affair;

import com.app.enums.AffairStatus;
import com.app.enums.Theme;
import com.app.model.advocate.Advocate;
import com.app.model.folder.Folder;
import com.app.model.judgement.Judgement;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="affair")
public class Affair {

    @Id
    @Column(name="affair_id")
    private Long id;
    private String number;
    private Date creationDate;
    private Date modificationDate;
    private Date closeDate;
    private Theme theme;

    private String prosecutor;
    private String Defendant;
    private String judiciary;

    private AffairStatus status;

    @ManyToOne
    @JoinColumn(name="folder_id", nullable=true, updatable=true)
    private Folder folder;

    @ManyToOne
    @JoinColumn(name="advocate_ID", nullable=true, updatable=true)
    private Advocate advocate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "affair", cascade = CascadeType.ALL)
    private Judgement judgement;

}
