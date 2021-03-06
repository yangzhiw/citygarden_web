package com.citygarden.web.rest;

import com.citygarden.config.SecurityConfiguration;
import com.citygarden.security.SecurityUtils;
import com.citygarden.service.DeliveryAddressService;
import com.citygarden.web.rest.dto.DeliveryAddressDTO;
import com.codahale.metrics.annotation.Timed;
import com.citygarden.domain.DeliveryAddress;
import com.citygarden.repository.DeliveryAddressRepository;
import com.citygarden.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DeliveryAddress.
 */
@RestController
@RequestMapping("/api")
public class DeliveryAddressResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryAddressResource.class);

    @Inject
    private DeliveryAddressRepository deliveryAddressRepository;

    @Inject
    private DeliveryAddressService deliveryAddressService;

    /**
     * POST  /deliveryAddresss -> Create a new deliveryAddress.
     */
    @RequestMapping(value = "/deliveryAddresss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeliveryAddress> createDeliveryAddress(@RequestBody DeliveryAddressDTO deliveryAddress) throws URISyntaxException {
        log.debug("REST request to save DeliveryAddress : {}", deliveryAddress);
        System.err.println(deliveryAddress);
        DeliveryAddress result = deliveryAddressService.save(deliveryAddress);
        return ResponseEntity.created(new URI("/api/deliveryAddresss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("deliveryAddress", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deliveryAddresss -> Updates an existing deliveryAddress.
     */
    @RequestMapping(value = "/deliveryAddresss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeliveryAddress> updateDeliveryAddress(@RequestBody DeliveryAddress deliveryAddress) throws URISyntaxException {
        log.debug("REST request to update DeliveryAddress : {}", deliveryAddress);
//        if (deliveryAddress.getId() == null) {
//            return createDeliveryAddress(deliveryAddress);
//        }
        DeliveryAddress result = deliveryAddressRepository.save(deliveryAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("deliveryAddress", deliveryAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deliveryAddresss -> get all the deliveryAddresss.
     */
    @RequestMapping(value = "/deliveryAddresss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DeliveryAddress> getAllDeliveryAddresss() {
        log.debug("REST request to get all DeliveryAddresss");
        String username = SecurityUtils.getCurrentUserLogin();
        return deliveryAddressRepository.findByUsername(username);
            }

    /**
     * GET  /deliveryAddresss/:id -> get the "id" deliveryAddress.
     */
    @RequestMapping(value = "/deliveryAddresss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeliveryAddress> getDeliveryAddress(@PathVariable String id) {
        log.debug("REST request to get DeliveryAddress : {}", id);
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findOne(id);
        return Optional.ofNullable(deliveryAddress)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /deliveryAddresss/:id -> delete the "id" deliveryAddress.
     */
    @RequestMapping(value = "/deliveryAddresss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDeliveryAddress(@PathVariable String id) {
        log.debug("REST request to delete DeliveryAddress : {}", id);
        deliveryAddressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("deliveryAddress", id.toString())).build();
    }
}
