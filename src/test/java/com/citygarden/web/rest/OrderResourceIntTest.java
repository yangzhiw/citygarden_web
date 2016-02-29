package com.citygarden.web.rest;

import com.citygarden.Application;
import com.citygarden.domain.Order;
import com.citygarden.repository.OrderRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the OrderResource REST controller.
 *
 * @see OrderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrderResourceIntTest {


    private static final Double DEFAULT_PRICE_COUNT = 1D;
    private static final Double UPDATED_PRICE_COUNT = 2D;

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOrderMockMvc;

    private Order order;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderResource orderResource = new OrderResource();
        ReflectionTestUtils.setField(orderResource, "orderRepository", orderRepository);
        this.restOrderMockMvc = MockMvcBuilders.standaloneSetup(orderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        orderRepository.deleteAll();
        order = new Order();
        order.setTotalPrice(DEFAULT_PRICE_COUNT);
    }

    @Test
    public void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order

        restOrderMockMvc.perform(post("/api/orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(order)))
                .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orders.get(orders.size() - 1);
        assertThat(testOrder.getTotalPrice()).isEqualTo(DEFAULT_PRICE_COUNT);
    }

    @Test
    public void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.save(order);

        // Get all the orders
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId())))
                .andExpect(jsonPath("$.[*].priceCount").value(hasItem(DEFAULT_PRICE_COUNT.doubleValue())));
    }

    @Test
    public void getOrder() throws Exception {
        // Initialize the database
        orderRepository.save(order);

        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(order.getId()))
            .andExpect(jsonPath("$.priceCount").value(DEFAULT_PRICE_COUNT.doubleValue()));
    }

    @Test
    public void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrder() throws Exception {
        // Initialize the database
        orderRepository.save(order);

		int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order
       // order.setPriceCount(UPDATED_PRICE_COUNT);

        restOrderMockMvc.perform(put("/api/orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(order)))
                .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orders.get(orders.size() - 1);
       // assertThat(testOrder.getPriceCount()).isEqualTo(UPDATED_PRICE_COUNT);
    }

    @Test
    public void deleteOrder() throws Exception {
        // Initialize the database
        orderRepository.save(order);

		int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Get the order
        restOrderMockMvc.perform(delete("/api/orders/{id}", order.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
