package com.worldpay.assignment.merchantoffer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * Merchant Entity object
 *
 * @author Nicholas Goldsworthy
 * @since 2018-01-07
 */
@Entity
@Table(name = "merchants")
public class Merchant {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name")
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
