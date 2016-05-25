package com.citygarden.web.rest;

import com.citygarden.security.SecurityUtils;
import com.codahale.metrics.annotation.Timed;
import com.citygarden.domain.UserLevel;
import com.citygarden.repository.UserLevelRepository;
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
 * REST controller for managing UserLevel.
 */
@RestController
@RequestMapping("/api")
public class UserLevelResource {

    private final Logger log = LoggerFactory.getLogger(UserLevelResource.class);

    @Inject
    private UserLevelRepository userLevelRepository;

    /**
     * POST  /userLevels -> Create a new userLevel.
     */
    @RequestMapping(value = "/userLevels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserLevel> createUserLevel(@RequestBody UserLevel userLevel) throws URISyntaxException {
        log.debug("REST request to save UserLevel : {}", userLevel);
        if (userLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userLevel", "idexists", "A new userLevel cannot already have an ID")).body(null);
        }
        UserLevel result = userLevelRepository.save(userLevel);
        return ResponseEntity.created(new URI("/api/userLevels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /userLevels -> Updates an existing userLevel.
     */
    @RequestMapping(value = "/userLevels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserLevel> updateUserLevel(@RequestBody UserLevel userLevel) throws URISyntaxException {
        log.debug("REST request to update UserLevel : {}", userLevel);
        if (userLevel.getId() == null) {
            return createUserLevel(userLevel);
        }
        UserLevel result = userLevelRepository.save(userLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userLevel", userLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /userLevels -> get all the userLevels.
     */
    @RequestMapping(value = "/userLevels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public UserLevel getAllUserLevels() {
        log.debug("REST request to get all UserLevels");
        String username = SecurityUtils.getCurrentUserLogin();

        return userLevelRepository.findByUserName(username);
            }

    /**
     * GET  /userLevels/:id -> get the "id" userLevel.
     */
    @RequestMapping(value = "/userLevels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserLevel> getUserLevel(@PathVariable String id) {
        log.debug("REST request to get UserLevel : {}", id);
        UserLevel userLevel = userLevelRepository.findOne(id);
        return Optional.ofNullable(userLevel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /userLevels/:id -> delete the "id" userLevel.
     */
    @RequestMapping(value = "/userLevels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUserLevel(@PathVariable String id) {
        log.debug("REST request to delete UserLevel : {}", id);
        userLevelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userLevel", id.toString())).build();
    }
}
