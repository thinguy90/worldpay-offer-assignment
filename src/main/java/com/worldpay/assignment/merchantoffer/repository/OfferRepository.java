package com.worldpay.assignment.merchantoffer.repository;

import com.worldpay.assignment.merchantoffer.domain.Offer;
import com.worldpay.assignment.merchantoffer.domain.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Offer JPA Respository
 *
 * @author Nicholas Goldsworthy
 * @since 2018-01-07
 */

@Repository
public interface OfferRepository extends CrudRepository<Offer,Long> {

    List<Offer> findAllByMerchantId(long merchantId);
    List<Offer> findAllByStatus(Status status);
}
