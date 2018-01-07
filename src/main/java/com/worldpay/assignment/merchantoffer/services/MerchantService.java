package com.worldpay.assignment.merchantoffer.services;

import com.worldpay.assignment.merchantoffer.domain.Merchant;
import com.worldpay.assignment.merchantoffer.domain.Offer;
import com.worldpay.assignment.merchantoffer.repository.MerchantRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * Merchant Service
 *
 * @author Nicholas Goldsworthy
 * @since 2018-01-07
 */
@Service
public class MerchantService {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(MerchantService.class);

    @Autowired
    MerchantRepository merchantRepository;

    /**
     * adds or updates a merchant
     * @param merchant
     */
    public void save(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    /**
     * find merchant
     * @param id merchant id
     * @return Optional<Merchant> if found or empty
     */
    public Optional<Merchant> find(long id) {
        try {
            return Optional.ofNullable(merchantRepository.findOne(id));
        } catch (Exception e) {
            logger.error("Merchant could not be found for id " + id);
            return Optional.empty();
        }
    }

    public List<Merchant> findAll() {
        List<Merchant> data = new ArrayList<>();
        merchantRepository.findAll().forEach(data::add);
        return data;
    }
}
