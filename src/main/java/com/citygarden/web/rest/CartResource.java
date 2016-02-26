package com.citygarden.web.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.citygarden.domain.CartDetails;
import com.citygarden.security.SecurityUtils;
import com.citygarden.service.CartService;
import com.codahale.metrics.annotation.Timed;
import com.citygarden.domain.Cart;
import com.citygarden.repository.CartRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cart.
 */
@RestController
@RequestMapping("/api")
public class CartResource {

    private final Logger log = LoggerFactory.getLogger(CartResource.class);

    @Inject
    private CartRepository cartRepository;

    @Inject
    private CartService cartService;

    /**
     * POST  /carts -> Create a new cart.
     */
    @RequestMapping(value = "/carts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) throws URISyntaxException {
        log.debug("REST request to save Cart : {}", cart);
        return null;
    }
    /**
     * PUT  /carts -> Updates an existing cart.
     */
    @RequestMapping(value = "/carts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cart> updateCart(@RequestBody Cart cart) throws URISyntaxException {
        log.debug("REST request to update Cart : {}", cart);
        Cart cart1 = cartService.updateCart(cart);
        if(cart1 == null){
            return  new ResponseEntity<Cart>(cart1,HttpStatus.NOT_FOUND);
        }else{
            return  new ResponseEntity<Cart>(cart1,HttpStatus.OK);
        }
    }

    /**
     * GET  /carts -> get all the carts.
     */
    @RequestMapping(value = "/carts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cart> getCarts(@CookieValue(value = "cartCookie",required  = false) String cartCookieStr) {
        log.debug("REST request to get all Carts");
        String username = SecurityUtils.getCurrentUserLogin();
        Cart cart = cartRepository.findByUsername(username);
        if(cart == null){
            return new ResponseEntity<Cart>(cart,HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<Cart>(cart,HttpStatus.OK);
        }
     }

    /**
     * GET  /carts/:id -> get the "id" cart.
     */
    @RequestMapping(value = "/carts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cart> getCart(@PathVariable String id) {
        log.debug("REST request to get Cart : {}", id);
        Cart cart = cartRepository.findOne(id);
        return Optional.ofNullable(cart)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /carts/:id -> delete the "id" cart.
     */
    @RequestMapping(value = "/carts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCart(@PathVariable String id) {
        log.debug("REST request to delete Cart : {}", id);
        cartRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cart", id.toString())).build();
    }
}
