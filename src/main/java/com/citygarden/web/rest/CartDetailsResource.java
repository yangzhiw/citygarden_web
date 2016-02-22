package com.citygarden.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.citygarden.domain.CartDetails;
import com.citygarden.repository.CartDetailsRepository;
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
 * REST controller for managing CartDetails.
 */
@RestController
@RequestMapping("/api")
public class CartDetailsResource {

    private final Logger log = LoggerFactory.getLogger(CartDetailsResource.class);
        
    @Inject
    private CartDetailsRepository cartDetailsRepository;
    
    /**
     * POST  /cartDetailss -> Create a new cartDetails.
     */
    @RequestMapping(value = "/cartDetailss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CartDetails> createCartDetails(@RequestBody CartDetails cartDetails) throws URISyntaxException {
        log.debug("REST request to save CartDetails : {}", cartDetails);
        if (cartDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cartDetails", "idexists", "A new cartDetails cannot already have an ID")).body(null);
        }
        CartDetails result = cartDetailsRepository.save(cartDetails);
        return ResponseEntity.created(new URI("/api/cartDetailss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cartDetails", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cartDetailss -> Updates an existing cartDetails.
     */
    @RequestMapping(value = "/cartDetailss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CartDetails> updateCartDetails(@RequestBody CartDetails cartDetails) throws URISyntaxException {
        log.debug("REST request to update CartDetails : {}", cartDetails);
        if (cartDetails.getId() == null) {
            return createCartDetails(cartDetails);
        }
        CartDetails result = cartDetailsRepository.save(cartDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cartDetails", cartDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cartDetailss -> get all the cartDetailss.
     */
    @RequestMapping(value = "/cartDetailss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CartDetails> getAllCartDetailss() {
        log.debug("REST request to get all CartDetailss");
        return cartDetailsRepository.findAll();
            }

    /**
     * GET  /cartDetailss/:id -> get the "id" cartDetails.
     */
    @RequestMapping(value = "/cartDetailss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CartDetails> getCartDetails(@PathVariable String id) {
        log.debug("REST request to get CartDetails : {}", id);
        CartDetails cartDetails = cartDetailsRepository.findOne(id);
        return Optional.ofNullable(cartDetails)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cartDetailss/:id -> delete the "id" cartDetails.
     */
    @RequestMapping(value = "/cartDetailss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCartDetails(@PathVariable String id) {
        log.debug("REST request to delete CartDetails : {}", id);
        cartDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cartDetails", id.toString())).build();
    }
}
