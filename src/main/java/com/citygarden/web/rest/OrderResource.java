package com.citygarden.web.rest;

import com.citygarden.service.OrderService;
import com.citygarden.web.rest.dto.OrderDTO;
import com.citygarden.web.rest.dto.PayOrderDTO;
import com.codahale.metrics.annotation.Timed;
import com.citygarden.domain.Order;
import com.citygarden.repository.OrderRepository;
import com.citygarden.web.rest.util.HeaderUtil;
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
 * REST controller for managing Order.
 */
@RestController
@RequestMapping("/api")
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private OrderService orderService;

    /**
     * POST  /orders -> Create a new order.
     */
    @RequestMapping(value = "/orders",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO order) throws URISyntaxException {

        log.debug("REST request to save Order : {}", order);
        System.out.println(order);

        Order result = orderService.save(order);

        return  new ResponseEntity<Order>(result,HttpStatus.OK);
    }

    /**
     * PUT  /orders -> Updates an existing order.
     */
    @RequestMapping(value = "/orders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) throws URISyntaxException {
        log.debug("REST request to update Order : {}", order);
//        if (order.getId() == null) {
//            return createOrder(order);
//        }
        Order result = orderRepository.save(order);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("order", order.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orders -> get all the orders.
     */
    @RequestMapping(value = "/orders",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Order> getAllOrders() {
        log.debug("REST request to get all Orders");
        return orderRepository.findAll();
            }

    /**
     * GET  /orders/:id -> get the "id" order.
     */
    @RequestMapping(value = "/orders/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Order> getOrder(@PathVariable String id) {
        log.debug("REST request to get Order : {}", id);
        System.err.println(id);
        Order order = orderRepository.findByOrderStatus(id);
        return Optional.ofNullable(order)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orders/:id -> delete the "id" order.
     */
    @RequestMapping(value = "/orders/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        log.debug("REST request to delete Order : {}", id);
        orderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("order", id.toString())).build();
    }

    @RequestMapping(value = "/orders/payment",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> payment(@RequestBody PayOrderDTO payOrder){
        orderService.payment(payOrder);
        return null;

    }
}
