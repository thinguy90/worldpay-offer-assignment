package com.worldpay.assignment.merchantoffer.services;

import com.worldpay.assignment.merchantoffer.domain.Merchant;
import com.worldpay.assignment.merchantoffer.repository.MerchantRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class MerchantServiceTest {

    @Mock
    MerchantRepository merchantRepository;

    @InjectMocks
    MerchantService merchantService;

    @Test
    public void find() {

        Merchant merchant1 = new Merchant();
        merchant1.setName("testing");
        merchant1.setId(1);

        Mockito.when(merchantRepository.findOne(merchant1.getId())).thenReturn(merchant1);

        Optional<Merchant> result = merchantService.find(merchant1.getId());
        assertThat(result.isPresent()).isTrue();

        result = merchantService.find(2);
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findAll() {

        Merchant merchant2 = new Merchant();
        merchant2.setName("testing 2");
        merchant2.setId(2);

        Merchant merchant1 = new Merchant();
        merchant1.setName("testing");
        merchant1.setId(1);

        Mockito.when(merchantRepository.findAll()).thenReturn(Arrays.asList(merchant1,merchant2));

        List<Merchant> merchantList = merchantService.findAll();
        assertThat(merchantList).containsExactly(merchant1, merchant2);
    }
}