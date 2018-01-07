package com.worldpay.assignment.merchantoffer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * Offer Entity object
 *
 * @author Nicholas Goldsworthy
 * @since 2018-01-07
 */
@Entity
@Table(name = "offers")
public class Offer implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "merchant_id")
    private long merchantId;

    @Column(name = "status")
    private Status status;

    @Column(name = "description")
    private String description;

    @Column(name = "time_to_live_minutes")
    private long timeToLiveMinutes;

    @Column(precision=16, scale=2)
    private BigDecimal price;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private Date createDateTime;

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimeToLiveMinutes() {
        return timeToLiveMinutes;
    }

    public void setTimeToLiveMinutes(long timeToLiveMinutes) {
        this.timeToLiveMinutes = timeToLiveMinutes;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "currency", length = 3)
    private String currency;

    public Offer clone()
    {
        Offer offer = new Offer();
        offer.setId(this.getId());
        offer.setStatus(this.getStatus());
        offer.setCurrency(this.getCurrency());
        offer.setMerchantId(this.getMerchantId());
        offer.setTimeToLiveMinutes(this.getTimeToLiveMinutes());
        offer.setDescription(this.getDescription());
        offer.setPrice(this.getPrice());
        offer.setCreateDateTime(this.getCreateDateTime());

        return offer;
    }


}
