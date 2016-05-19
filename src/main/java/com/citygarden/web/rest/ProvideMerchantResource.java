package com.citygarden.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.citygarden.domain.ProvideMerchant;
import com.citygarden.service.ProvideMerchantService;
import com.citygarden.web.rest.util.HeaderUtil;
import com.citygarden.web.rest.dto.ProvideMerchantDTO;
import com.citygarden.web.rest.mapper.ProvideMerchantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing ProvideMerchant.
 */
@RestController
@RequestMapping("/api")
public class ProvideMerchantResource {

    private final Logger log = LoggerFactory.getLogger(ProvideMerchantResource.class);
        
    @Inject
    private ProvideMerchantService provideMerchantService;
    
    
    /**
     * POST  /provideMerchants -> Create a new provideMerchant.
     */
    @RequestMapping(value = "/provideMerchants",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProvideMerchantDTO> createProvideMerchant(@RequestBody ProvideMerchantDTO provideMerchantDTO) throws URISyntaxException {
        log.debug("REST request to save ProvideMerchant : {}", provideMerchantDTO);
        if (provideMerchantDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("provideMerchant", "idexists", "A new provideMerchant cannot already have an ID")).body(null);
        }
        ProvideMerchantDTO result = provideMerchantService.save(provideMerchantDTO);
        return ResponseEntity.created(new URI("/api/provideMerchants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("provideMerchant", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /provideMerchants -> Updates an existing provideMerchant.
     */
    @RequestMapping(value = "/provideMerchants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProvideMerchantDTO> updateProvideMerchant(@RequestBody ProvideMerchantDTO provideMerchantDTO) throws URISyntaxException {
        log.debug("REST request to update ProvideMerchant : {}", provideMerchantDTO);
        if (provideMerchantDTO.getId() == null) {
            return createProvideMerchant(provideMerchantDTO);
        }
        ProvideMerchantDTO result = provideMerchantService.save(provideMerchantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("provideMerchant", provideMerchantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provideMerchants -> get all the provideMerchants.
     */
    @RequestMapping(value = "/provideMerchants",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<ProvideMerchantDTO> getAllProvideMerchants() {
        log.debug("REST request to get all ProvideMerchants");
        return provideMerchantService.findAll();
            }

    /**
     * GET  /provideMerchants/:id -> get the "id" provideMerchant.
     */
    @RequestMapping(value = "/provideMerchants/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProvideMerchantDTO> getProvideMerchant(@PathVariable String id) {
        log.debug("REST request to get ProvideMerchant : {}", id);
        ProvideMerchantDTO provideMerchantDTO = provideMerchantService.findOne(id);
        return Optional.ofNullable(provideMerchantDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /provideMerchants/:id -> delete the "id" provideMerchant.
     */
    @RequestMapping(value = "/provideMerchants/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProvideMerchant(@PathVariable String id) {
        log.debug("REST request to delete ProvideMerchant : {}", id);
        provideMerchantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("provideMerchant", id.toString())).build();
    }
}
