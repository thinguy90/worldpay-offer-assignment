package com.worldpay.assignment.merchantoffer.services;

import com.worldpay.assignment.merchantoffer.domain.Merchant;
import com.worldpay.assignment.merchantoffer.repository.MerchantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MerchantServiceIntegrationTest {

    @Autowired
    MerchantService merchantService;

    @Autowired
    MerchantRepository merchantRepository;

    Merchant merchant1;
    Merchant merchant2;

    @Before
    public void setup()
    {
        merchant1 = new Merchant();
        merchant1.setName("testing1");

        merchant2 = new Merchant();
        merchant2.setName("testing2");

        merchantRepository.save(merchant1);
        merchantRepository.save(merchant2);
    }

    @Test
    public void save() {

        Merchant merchant = new Merchant();
        merchant.setName("testing");

        merchantService.save(merchant);
        assertThat(merchant.getId()).isGreaterThan(0);
    }

    @Test
    public void find() {
        Optional<Merchant> result = merchantService.find(merchant1.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(merchant1.getId());
        assertThat(result.get().getName()).isEqualTo(merchant1.getName());

        result = merchantService.find(5000);
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findAll() {
        List<Merchant> results = merchantService.findAll();
        assertThat(results.stream().map(Merchant::getId).collect(Collectors.toList())).contains(merchant1.getId(),merchant2.getId());

    }
}