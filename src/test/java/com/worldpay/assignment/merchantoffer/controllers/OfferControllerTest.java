package com.worldpay.assignment.merchantoffer.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldpay.assignment.merchantoffer.domain.Merchant;
import com.worldpay.assignment.merchantoffer.domain.Offer;
import com.worldpay.assignment.merchantoffer.domain.Status;
import com.worldpay.assignment.merchantoffer.repository.MerchantRepository;
import com.worldpay.assignment.merchantoffer.services.MerchantService;
import com.worldpay.assignment.merchantoffer.services.OfferService;
import com.worldpay.assignment.merchantoffer.services.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class OfferControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MerchantService merchantService;

    @MockBean
    OfferService offerService;

    Offer offer1;
    List<Offer> allOffers;

    @Before
    public void setup() {
        offer1 = new Offer();
        offer1.setId(1000);
        offer1.setMerchantId(1);
        offer1.setCreateDateTime(new Date());
        offer1.setStatus(Status.ACTIVE);
        offer1.setPrice(BigDecimal.valueOf(500));
        offer1.setDescription("testing description");
        offer1.setCurrency("ZAR");
        offer1.setTimeToLiveMinutes(5);

        allOffers = Arrays.asList(offer1);
    }

    @Test
    public void getOffers() throws Exception {

        when(offerService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/offers")).andDo(print())
                .andExpect(status().isOk());

        when(offerService.findAll()).thenReturn(allOffers);

        mockMvc.perform(get("/offers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("1000"));
    }

    @Test
    public void getOffer() throws Exception {

        when(offerService.find(offer1.getId())).thenReturn(Optional.empty());

        mockMvc.perform(get("/offers/" + offer1.getId())).andDo(print())
                .andExpect(status().isNotFound());

        when(offerService.find(offer1.getId())).thenReturn(Optional.of(offer1));
        mockMvc.perform(get("/offers/" + offer1.getId())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(offer1.getId()));
    }

    @Test
    public void getMerchantOffers() throws Exception {

        when(offerService.findOffersByMerchant(offer1.getId())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/offers/merchant/" + offer1.getMerchantId())).andDo(print())
                .andExpect(status().isOk());

        when(offerService.find(offer1.getId())).thenReturn(Optional.of(offer1));
        
        mockMvc.perform(get("/offers/" + offer1.getId())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(offer1.getId()));

    }

    @Test
    public void add() throws Exception {

        mockMvc.perform(
                post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(offer1)))
                .andDo(print())
                .andExpect(status().isCreated());


        Mockito.doThrow(new ValidationException("mock", offer1))
                .when(offerService)
                .add(any() );

        mockMvc.perform(
                post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(offer1)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void update() throws Exception {

        mockMvc.perform(
                put("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(offer1)))
                .andDo(print())
                .andExpect(status().isOk());


        Mockito.doThrow(new ValidationException("mock", offer1))
                .when(offerService)
                .update(any() );

        mockMvc.perform(
                put("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(offer1)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void updateStatus() throws Exception {

        Mockito.when(offerService.find(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(
                patch("/offers/"+ offer1.getId() +"/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("newStatus", Status.ACTIVE.toString()))

                .andDo(print())
                .andExpect(status().isNotFound());

        Mockito.when(offerService.find(offer1.getId())).thenReturn(Optional.of(offer1));

        mockMvc.perform(
                patch("/offers/"+ offer1.getId() +"/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("newStatus", Status.ACTIVE.toString()))

                .andDo(print())
                .andExpect(status().isOk());


        Mockito.doThrow(new ValidationException("mock", offer1))
                .when(offerService)
                .update(any() );

        mockMvc.perform(
                patch("/offers/"+ offer1.getId() +"/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(offer1)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}