package com.citygarden.web.rest;

import com.citygarden.Application;
import com.citygarden.domain.DeliveryAddress;
import com.citygarden.repository.DeliveryAddressRepository;

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
 * Test class for the DeliveryAddressResource REST controller.
 *
 * @see DeliveryAddressResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DeliveryAddressResourceIntTest {


    @Inject
    private DeliveryAddressRepository deliveryAddressRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDeliveryAddressMockMvc;

    private DeliveryAddress deliveryAddress;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeliveryAddressResource deliveryAddressResource = new DeliveryAddressResource();
        ReflectionTestUtils.setField(deliveryAddressResource, "deliveryAddressRepository", deliveryAddressRepository);
        this.restDeliveryAddressMockMvc = MockMvcBuilders.standaloneSetup(deliveryAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        deliveryAddressRepository.deleteAll();
        deliveryAddress = new DeliveryAddress();
    }

    @Test
    public void createDeliveryAddress() throws Exception {
        int databaseSizeBeforeCreate = deliveryAddressRepository.findAll().size();

        // Create the DeliveryAddress

        restDeliveryAddressMockMvc.perform(post("/api/deliveryAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deliveryAddress)))
                .andExpect(status().isCreated());

        // Validate the DeliveryAddress in the database
        List<DeliveryAddress> deliveryAddresss = deliveryAddressRepository.findAll();
        assertThat(deliveryAddresss).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryAddress testDeliveryAddress = deliveryAddresss.get(deliveryAddresss.size() - 1);
    }

    @Test
    public void getAllDeliveryAddresss() throws Exception {
        // Initialize the database
        deliveryAddressRepository.save(deliveryAddress);

        // Get all the deliveryAddresss
        restDeliveryAddressMockMvc.perform(get("/api/deliveryAddresss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryAddress.getId())));
    }

    @Test
    public void getDeliveryAddress() throws Exception {
        // Initialize the database
        deliveryAddressRepository.save(deliveryAddress);

        // Get the deliveryAddress
        restDeliveryAddressMockMvc.perform(get("/api/deliveryAddresss/{id}", deliveryAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(deliveryAddress.getId()));
    }

    @Test
    public void getNonExistingDeliveryAddress() throws Exception {
        // Get the deliveryAddress
        restDeliveryAddressMockMvc.perform(get("/api/deliveryAddresss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateDeliveryAddress() throws Exception {
        // Initialize the database
        deliveryAddressRepository.save(deliveryAddress);

		int databaseSizeBeforeUpdate = deliveryAddressRepository.findAll().size();

        // Update the deliveryAddress

        restDeliveryAddressMockMvc.perform(put("/api/deliveryAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deliveryAddress)))
                .andExpect(status().isOk());

        // Validate the DeliveryAddress in the database
        List<DeliveryAddress> deliveryAddresss = deliveryAddressRepository.findAll();
        assertThat(deliveryAddresss).hasSize(databaseSizeBeforeUpdate);
        DeliveryAddress testDeliveryAddress = deliveryAddresss.get(deliveryAddresss.size() - 1);
    }

    @Test
    public void deleteDeliveryAddress() throws Exception {
        // Initialize the database
        deliveryAddressRepository.save(deliveryAddress);

		int databaseSizeBeforeDelete = deliveryAddressRepository.findAll().size();

        // Get the deliveryAddress
        restDeliveryAddressMockMvc.perform(delete("/api/deliveryAddresss/{id}", deliveryAddress.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DeliveryAddress> deliveryAddresss = deliveryAddressRepository.findAll();
        assertThat(deliveryAddresss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
