package com.citygarden.web.rest;


import com.citygarden.domain.Cart;
import com.citygarden.service.DishPhotoUtilService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing Cart.
 */
@RestController
@RequestMapping("/api")
public class DishPhotoResource {
    private final Logger log = LoggerFactory.getLogger(DishPhotoResource.class);

    @Inject
    private DishPhotoUtilService dishPhotoUtilService;
    /**
     * GET  /dishPhoto/:id -> get the "id" dishPhoto.
     */
    @RequestMapping(value = "/dishPhoto/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Map getCart(@PathVariable String name) throws Exception {
        log.debug("REST request to get Cart : {}", name);
        String dishPhoto = dishPhotoUtilService.getDishPhoto(name);
        Map<String,String> map = new HashMap<>();
        map.put("dphoto",dishPhoto);
        return map;
    }

}
