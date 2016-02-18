package com.citygarden.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.citygarden.domain.VegetableProvide;
import com.citygarden.repository.VegetableProvideRepository;
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
 * REST controller for managing VegetableProvide.
 */
@RestController
@RequestMapping("/api")
public class VegetableProvideResource {

    private final Logger log = LoggerFactory.getLogger(VegetableProvideResource.class);
        
    @Inject
    private VegetableProvideRepository vegetableProvideRepository;
    
    /**
     * POST  /vegetableProvides -> Create a new vegetableProvide.
     */
    @RequestMapping(value = "/vegetableProvides",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VegetableProvide> createVegetableProvide(@RequestBody VegetableProvide vegetableProvide) throws URISyntaxException {
        log.debug("REST request to save VegetableProvide : {}", vegetableProvide);
        if (vegetableProvide.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("vegetableProvide", "idexists", "A new vegetableProvide cannot already have an ID")).body(null);
        }
        VegetableProvide result = vegetableProvideRepository.save(vegetableProvide);
        return ResponseEntity.created(new URI("/api/vegetableProvides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("vegetableProvide", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vegetableProvides -> Updates an existing vegetableProvide.
     */
    @RequestMapping(value = "/vegetableProvides",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VegetableProvide> updateVegetableProvide(@RequestBody VegetableProvide vegetableProvide) throws URISyntaxException {
        log.debug("REST request to update VegetableProvide : {}", vegetableProvide);
        if (vegetableProvide.getId() == null) {
            return createVegetableProvide(vegetableProvide);
        }
        VegetableProvide result = vegetableProvideRepository.save(vegetableProvide);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("vegetableProvide", vegetableProvide.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vegetableProvides -> get all the vegetableProvides.
     */
    @RequestMapping(value = "/vegetableProvides",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<VegetableProvide> getAllVegetableProvides() {
        log.debug("REST request to get all VegetableProvides");
        return vegetableProvideRepository.findAll();
            }

    /**
     * GET  /vegetableProvides/:id -> get the "id" vegetableProvide.
     */
    @RequestMapping(value = "/vegetableProvides/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VegetableProvide> getVegetableProvide(@PathVariable String id) {
        log.debug("REST request to get VegetableProvide : {}", id);
        VegetableProvide vegetableProvide = vegetableProvideRepository.findOne(id);
        return Optional.ofNullable(vegetableProvide)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vegetableProvides/:id -> delete the "id" vegetableProvide.
     */
    @RequestMapping(value = "/vegetableProvides/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVegetableProvide(@PathVariable String id) {
        log.debug("REST request to delete VegetableProvide : {}", id);
        vegetableProvideRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vegetableProvide", id.toString())).build();
    }
}
