package com.app.model.transmission;

import javax.persistence.Column;
import javax.persistence.Id;

public class Transmission {

    @Id
    @Column(name="advocate_id")
    private Long id;
    private Long amount;

}
