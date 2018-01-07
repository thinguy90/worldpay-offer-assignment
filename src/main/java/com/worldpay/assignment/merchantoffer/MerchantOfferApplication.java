package com.worldpay.assignment.merchantoffer;

import com.worldpay.assignment.merchantoffer.domain.Merchant;
import com.worldpay.assignment.merchantoffer.domain.Offer;
import com.worldpay.assignment.merchantoffer.domain.Status;
import com.worldpay.assignment.merchantoffer.services.MerchantService;
import com.worldpay.assignment.merchantoffer.services.OfferService;
import com.worldpay.assignment.merchantoffer.services.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableScheduling
public class MerchantOfferApplication implements ApplicationListener<ApplicationEvent> {

	@Autowired
	MerchantService merchantService;

	@Autowired
	OfferService offerService;

	public static void main(String[] args) {
		SpringApplication.run(MerchantOfferApplication.class, args);
	}


	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		if (applicationEvent.getSource() instanceof EmbeddedServletContainer) {

			setupTestData();
		}
	}

    /**
     * creates test data for the purpose of this assignment to illustrate functionality
     */
	private void setupTestData() {
		Merchant merchant = new Merchant();
		merchant.setName("Nicholas");

		merchantService.save(merchant);

		Optional<Merchant> merchant1 = merchantService.find(merchant.getId());

		Offer offer = new Offer();
		offer.setCurrency("USD");
		offer.setDescription("My cool description");
		offer.setMerchantId(merchant.getId());
		offer.setPrice(BigDecimal.valueOf(550.95));
		offer.setStatus(Status.ACTIVE);
		offer.setTimeToLiveMinutes(15);
		offer.setCreateDateTime(new Date());

		try {
            offerService.add(offer);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

	}

}
