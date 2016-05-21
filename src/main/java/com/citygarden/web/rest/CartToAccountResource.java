package com.citygarden.web.rest;


import com.citygarden.domain.CartToAccount;
import com.citygarden.domain.Order;
import com.citygarden.repository.CartToAccountRepository;
import com.citygarden.security.SecurityUtils;
import com.citygarden.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Administrator on 2016/2/29 0029.
 */
@RestController
@RequestMapping("/api")
public class CartToAccountResource {
    private final Logger log = LoggerFactory.getLogger(CartToAccountResource.class);

    @Inject
    private CartToAccountRepository cartToAccountRepository;

    /**
     * POST  /cart2accounts -> Create a new orderItem.
     */
    @RequestMapping(value = "/cart2accounts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CartToAccount> createToAccount(@RequestBody CartToAccount toAccount) throws URISyntaxException {

        log.debug("REST request to save Order : {}", toAccount);
        System.out.println(toAccount);
        if (toAccount.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("order", "idexists", "A new order cannot already have an ID")).body(null);
        }
        toAccount.setUsername(SecurityUtils.getCurrentUserLogin());
        CartToAccount result = cartToAccountRepository.save(toAccount);

        return  new ResponseEntity<CartToAccount>(result, HttpStatus.OK);
    }

    /**
     * GET  /orders/:id -> get the "id" order.
     */
    @RequestMapping(value = "/cart2accounts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CartToAccount> getOrders(@PathVariable String id) {
        log.debug("REST request to get Order : {}", id);
        System.err.println(id);
        String username = SecurityUtils.getCurrentUserLogin();
        CartToAccount result = cartToAccountRepository.findByOrderStatusAndUsername(id, username);
        return new ResponseEntity<CartToAccount>(result, HttpStatus.OK);
    }

    /**
     * DELETE  /carts/:id -> delete the "id" cart.
     */
    @RequestMapping(value = "/cart2accounts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCart(@PathVariable String id) {
        log.debug("REST request to delete Cart : {}", id);
        cartToAccountRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cart", id.toString())).build();
    }
}
