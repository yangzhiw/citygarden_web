package com.citygarden.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.citygarden.domain.GroupPurchase;
import com.citygarden.repository.GroupPurchaseRepository;
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
 * REST controller for managing GroupPurchase.
 */
@RestController
@RequestMapping("/api")
public class GroupPurchaseResource {

    private final Logger log = LoggerFactory.getLogger(GroupPurchaseResource.class);
        
    @Inject
    private GroupPurchaseRepository groupPurchaseRepository;
    
    /**
     * POST  /groupPurchases -> Create a new groupPurchase.
     */
    @RequestMapping(value = "/groupPurchases",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GroupPurchase> createGroupPurchase(@RequestBody GroupPurchase groupPurchase) throws URISyntaxException {
        log.debug("REST request to save GroupPurchase : {}", groupPurchase);
        if (groupPurchase.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("groupPurchase", "idexists", "A new groupPurchase cannot already have an ID")).body(null);
        }
        GroupPurchase result = groupPurchaseRepository.save(groupPurchase);
        return ResponseEntity.created(new URI("/api/groupPurchases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("groupPurchase", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /groupPurchases -> Updates an existing groupPurchase.
     */
    @RequestMapping(value = "/groupPurchases",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GroupPurchase> updateGroupPurchase(@RequestBody GroupPurchase groupPurchase) throws URISyntaxException {
        log.debug("REST request to update GroupPurchase : {}", groupPurchase);
        if (groupPurchase.getId() == null) {
            return createGroupPurchase(groupPurchase);
        }
        GroupPurchase result = groupPurchaseRepository.save(groupPurchase);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("groupPurchase", groupPurchase.getId().toString()))
            .body(result);
    }

    /**
     * GET  /groupPurchases -> get all the groupPurchases.
     */
    @RequestMapping(value = "/groupPurchases",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GroupPurchase> getAllGroupPurchases() {
        log.debug("REST request to get all GroupPurchases");
        return groupPurchaseRepository.findAll();
            }

    /**
     * GET  /groupPurchases/:id -> get the "id" groupPurchase.
     */
    @RequestMapping(value = "/groupPurchases/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GroupPurchase> getGroupPurchase(@PathVariable String id) {
        log.debug("REST request to get GroupPurchase : {}", id);
        GroupPurchase groupPurchase = groupPurchaseRepository.findOne(id);
        return Optional.ofNullable(groupPurchase)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /groupPurchases/:id -> delete the "id" groupPurchase.
     */
    @RequestMapping(value = "/groupPurchases/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGroupPurchase(@PathVariable String id) {
        log.debug("REST request to delete GroupPurchase : {}", id);
        groupPurchaseRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("groupPurchase", id.toString())).build();
    }
}
