package com.worldpay.assignment.merchantoffer.controllers;

import com.worldpay.assignment.merchantoffer.domain.Merchant;
import com.worldpay.assignment.merchantoffer.services.MerchantService;
import com.worldpay.assignment.merchantoffer.services.OfferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MerchantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MerchantService merchantService;

    @MockBean
    OfferService offerService;

    @Test
    public void getMerchants()throws Exception {

        Merchant merchant = new Merchant();
        merchant.setName("testing");
        merchant.setId(1);
        List<Merchant> merchantList = Arrays.asList(merchant);

        when(merchantService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/merchants")).andDo(print())
                .andExpect(status().isOk());

        when(merchantService.findAll()).thenReturn(merchantList);

        mockMvc.perform(get("/merchants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray() )
                .andExpect(jsonPath("$[0].name").value("testing"));
    }
}