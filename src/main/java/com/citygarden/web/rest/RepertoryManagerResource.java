package com.citygarden.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.citygarden.domain.RepertoryManager;
import com.citygarden.repository.RepertoryManagerRepository;
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
 * REST controller for managing RepertoryManager.
 */
@RestController
@RequestMapping("/api")
public class RepertoryManagerResource {

    private final Logger log = LoggerFactory.getLogger(RepertoryManagerResource.class);
        
    @Inject
    private RepertoryManagerRepository repertoryManagerRepository;
    
    /**
     * POST  /repertoryManagers -> Create a new repertoryManager.
     */
    @RequestMapping(value = "/repertoryManagers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RepertoryManager> createRepertoryManager(@RequestBody RepertoryManager repertoryManager) throws URISyntaxException {
        log.debug("REST request to save RepertoryManager : {}", repertoryManager);
        if (repertoryManager.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("repertoryManager", "idexists", "A new repertoryManager cannot already have an ID")).body(null);
        }
        RepertoryManager result = repertoryManagerRepository.save(repertoryManager);
        return ResponseEntity.created(new URI("/api/repertoryManagers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("repertoryManager", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /repertoryManagers -> Updates an existing repertoryManager.
     */
    @RequestMapping(value = "/repertoryManagers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RepertoryManager> updateRepertoryManager(@RequestBody RepertoryManager repertoryManager) throws URISyntaxException {
        log.debug("REST request to update RepertoryManager : {}", repertoryManager);
        if (repertoryManager.getId() == null) {
            return createRepertoryManager(repertoryManager);
        }
        RepertoryManager result = repertoryManagerRepository.save(repertoryManager);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("repertoryManager", repertoryManager.getId().toString()))
            .body(result);
    }

    /**
     * GET  /repertoryManagers -> get all the repertoryManagers.
     */
    @RequestMapping(value = "/repertoryManagers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RepertoryManager> getAllRepertoryManagers() {
        log.debug("REST request to get all RepertoryManagers");
        return repertoryManagerRepository.findAll();
            }

    /**
     * GET  /repertoryManagers/:id -> get the "id" repertoryManager.
     */
    @RequestMapping(value = "/repertoryManagers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RepertoryManager> getRepertoryManager(@PathVariable String id) {
        log.debug("REST request to get RepertoryManager : {}", id);
        RepertoryManager repertoryManager = repertoryManagerRepository.findOne(id);
        return Optional.ofNullable(repertoryManager)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /repertoryManagers/:id -> delete the "id" repertoryManager.
     */
    @RequestMapping(value = "/repertoryManagers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRepertoryManager(@PathVariable String id) {
        log.debug("REST request to delete RepertoryManager : {}", id);
        repertoryManagerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("repertoryManager", id.toString())).build();
    }
}
