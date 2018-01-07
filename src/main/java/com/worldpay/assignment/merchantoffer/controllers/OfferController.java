package com.worldpay.assignment.merchantoffer.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldpay.assignment.merchantoffer.controllers.exceptions.NotFoundException;
import com.worldpay.assignment.merchantoffer.services.ValidationException;
import com.worldpay.assignment.merchantoffer.domain.Offer;
import com.worldpay.assignment.merchantoffer.domain.Status;
import com.worldpay.assignment.merchantoffer.services.MerchantService;
import com.worldpay.assignment.merchantoffer.services.OfferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 *
 * REST Controller for Offers
 *
 * @author Nicholas Goldsworthy
 * @since 2018-01-07
 */
@RestController
@RequestMapping(value = "offers", produces = "application/json")
@Api(tags = "offers")
public class OfferController {

    @Autowired
    OfferService offerService;

    @Autowired
    MerchantService merchantService;

    @GetMapping
    public List<Offer> getOffers() {
        return offerService.findAll();
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<Offer> getOffer(@PathVariable long offerId) throws NotFoundException {
        return offerService.find(offerId)
                .map(offer -> new ResponseEntity<Offer>(offer, HttpStatus.OK))
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping("/merchant/{merchantId}")
    public List<Offer> getMerchantOffers(@PathVariable long merchantId) {
        return offerService.findOffersByMerchant(merchantId);
    }

    @PostMapping()
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Validation error occured", response = String.class),
    })
    ResponseEntity<Void> add(@RequestBody Offer offer) throws ValidationException {

        offerService.add(offer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(offer.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @PutMapping()
    ResponseEntity update(@RequestBody Offer input) throws ValidationException {

        offerService.update(input);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("{offerId}/status")
    public ResponseEntity updateStatus(@PathVariable long offerId, @RequestParam Status newStatus) throws NotFoundException, ValidationException {

        Optional<Offer> offerOptional = offerService.find(offerId);

        if (offerOptional.isPresent()) {
            Offer offer = offerOptional.get();
            offer.setStatus(newStatus);
            offerService.update(offer);
            return new ResponseEntity(HttpStatus.OK);
        }

        throw new NotFoundException();
    }


    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handle(ValidationException e) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(e.getMessage());
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
        return e.getMessage();
    }
}
