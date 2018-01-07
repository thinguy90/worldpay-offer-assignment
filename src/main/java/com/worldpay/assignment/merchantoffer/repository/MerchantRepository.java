package com.worldpay.assignment.merchantoffer.repository;

import com.worldpay.assignment.merchantoffer.domain.Merchant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * Merchant JPA Respository
 *
 * @author Nicholas Goldsworthy
 * @since 2018-01-07
 */
@Repository
public interface MerchantRepository extends CrudRepository<Merchant,Long> {
}
