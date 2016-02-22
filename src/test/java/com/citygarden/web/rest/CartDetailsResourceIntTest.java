package com.citygarden.web.rest;

import com.citygarden.Application;
import com.citygarden.domain.CartDetails;
import com.citygarden.repository.CartDetailsRepository;

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
 * Test class for the CartDetailsResource REST controller.
 *
 * @see CartDetailsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CartDetailsResourceIntTest {


    @Inject
    private CartDetailsRepository cartDetailsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCartDetailsMockMvc;

    private CartDetails cartDetails;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CartDetailsResource cartDetailsResource = new CartDetailsResource();
        ReflectionTestUtils.setField(cartDetailsResource, "cartDetailsRepository", cartDetailsRepository);
        this.restCartDetailsMockMvc = MockMvcBuilders.standaloneSetup(cartDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cartDetailsRepository.deleteAll();
        cartDetails = new CartDetails();
    }

    @Test
    public void createCartDetails() throws Exception {
        int databaseSizeBeforeCreate = cartDetailsRepository.findAll().size();

        // Create the CartDetails

        restCartDetailsMockMvc.perform(post("/api/cartDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDetails)))
                .andExpect(status().isCreated());

        // Validate the CartDetails in the database
        List<CartDetails> cartDetailss = cartDetailsRepository.findAll();
        assertThat(cartDetailss).hasSize(databaseSizeBeforeCreate + 1);
        CartDetails testCartDetails = cartDetailss.get(cartDetailss.size() - 1);
    }

    @Test
    public void getAllCartDetailss() throws Exception {
        // Initialize the database
        cartDetailsRepository.save(cartDetails);

        // Get all the cartDetailss
        restCartDetailsMockMvc.perform(get("/api/cartDetailss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cartDetails.getId())));
    }

    @Test
    public void getCartDetails() throws Exception {
        // Initialize the database
        cartDetailsRepository.save(cartDetails);

        // Get the cartDetails
        restCartDetailsMockMvc.perform(get("/api/cartDetailss/{id}", cartDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cartDetails.getId()));
    }

    @Test
    public void getNonExistingCartDetails() throws Exception {
        // Get the cartDetails
        restCartDetailsMockMvc.perform(get("/api/cartDetailss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCartDetails() throws Exception {
        // Initialize the database
        cartDetailsRepository.save(cartDetails);

		int databaseSizeBeforeUpdate = cartDetailsRepository.findAll().size();

        // Update the cartDetails

        restCartDetailsMockMvc.perform(put("/api/cartDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDetails)))
                .andExpect(status().isOk());

        // Validate the CartDetails in the database
        List<CartDetails> cartDetailss = cartDetailsRepository.findAll();
        assertThat(cartDetailss).hasSize(databaseSizeBeforeUpdate);
        CartDetails testCartDetails = cartDetailss.get(cartDetailss.size() - 1);
    }

    @Test
    public void deleteCartDetails() throws Exception {
        // Initialize the database
        cartDetailsRepository.save(cartDetails);

		int databaseSizeBeforeDelete = cartDetailsRepository.findAll().size();

        // Get the cartDetails
        restCartDetailsMockMvc.perform(delete("/api/cartDetailss/{id}", cartDetails.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CartDetails> cartDetailss = cartDetailsRepository.findAll();
        assertThat(cartDetailss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
