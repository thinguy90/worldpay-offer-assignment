package com.worldpay.assignment.merchantoffer.services;

import com.worldpay.assignment.merchantoffer.domain.Merchant;
import com.worldpay.assignment.merchantoffer.domain.Offer;
import com.worldpay.assignment.merchantoffer.domain.Status;
import com.worldpay.assignment.merchantoffer.repository.MerchantRepository;
import com.worldpay.assignment.merchantoffer.repository.OfferRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceTest {

    @Mock
    MerchantRepository merchantRepository;

    @Mock
    OfferRepository offerRepository;

    @InjectMocks
    OfferService offerService;

    @Mock
    MerchantService merchantService;

    Merchant merchant = new Merchant();

    @Before
    public void setup() {
        merchant = new Merchant();
        merchant.setId(50000);
        merchant.setName("test merchant");
    }

    @Test
    public void validateStatus() {

        Offer offer = new Offer();
        offer.setCurrency("USD");
        offer.setId(5000);
        offer.setMerchantId(merchant.getId());
        offer.setStatus(Status.ACTIVE);


        Offer expiredOffer = new Offer();
        expiredOffer.setStatus(Status.EXPIRED);
        expiredOffer.setId(offer.getId());


        when(offerRepository.findOne(new Long(offer.getId()))).thenReturn(expiredOffer);

        Throwable thrown = catchThrowable(() -> {
            offerService.validateStatus(offer);
        });
        assertThat(thrown).isInstanceOf(ValidationException.class).hasNoCause().hasMessageStartingWith("Expired offers cannot be updated");

        when(offerRepository.findOne(new Long(offer.getId()))).thenReturn(offer);
        thrown = catchThrowable(() -> {
            offerService.validateStatus(offer);
        });
        assertThat(thrown).isNull();
    }

    @Test
    public void validateCurrency() {

        Offer offer = new Offer();
        offer.setCurrency("USDaaa");
        offer.setMerchantId(merchant.getId());
        offer.setStatus(Status.ACTIVE);

        Throwable thrown = catchThrowable(() -> {
            offerService.validateCurrency(offer);
        });
        assertThat(thrown).isInstanceOf(ValidationException.class).hasNoCause().hasMessageStartingWith("Currency must be 3 characters long");

        offer.setCurrency("USD");
        thrown = catchThrowable(() -> {
            offerService.validateCurrency(offer);
        });
        assertThat(thrown).isNull();

    }

    @Test
    public void validateMerchantExists() throws Exception {

        Offer offer = new Offer();
        offer.setCurrency("USD");
        offer.setMerchantId(merchant.getId());
        offer.setStatus(Status.ACTIVE);

        Throwable thrown = catchThrowable(() -> {
            offerService.validateMerchantExists(offer);
        });
        assertThat(thrown).isInstanceOf(ValidationException.class).hasNoCause().hasMessageStartingWith("Merchant does not exist");

        when(merchantService.find(offer.getMerchantId())).thenReturn(Optional.of(merchant));
        when(offerService.merchantService.find(offer.getMerchantId())).thenReturn(Optional.of(merchant));

        thrown = catchThrowable(() -> {
            offerService.validateMerchantExists(offer);
        });
        assertThat(thrown).isNull();
    }

    @Test
    public void validateOfferExists() {

        Offer offer = new Offer();
        offer.setCurrency("USD");
        offer.setMerchantId(merchant.getId());
        offer.setStatus(Status.ACTIVE);
        offer.setId(40000);

        when(offerService.find(offer.getId())).thenReturn(Optional.of(offer));

        Throwable thrown = catchThrowable(() -> {
            offerService.validateOfferExists(offer);
        });
        assertThat(thrown).isInstanceOf(ValidationException.class).hasNoCause().hasMessageStartingWith("Offer does not exist");

        when(merchantService.find(offer.getMerchantId())).thenReturn(Optional.of(merchant));
        when(offerService.merchantService.find(offer.getMerchantId())).thenReturn(Optional.of(merchant));

        thrown = catchThrowable(() -> {
            offerService.validateOfferExists(offer);
        });
        assertThat(thrown).isNull();


    }
}