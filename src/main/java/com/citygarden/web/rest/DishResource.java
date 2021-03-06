package com.citygarden.web.rest;

import com.citygarden.service.DishService;
import com.citygarden.web.rest.dto.DishDTO;
import com.codahale.metrics.annotation.Timed;
import com.citygarden.domain.Dish;
import com.citygarden.repository.DishRepository;
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
 * REST controller for managing Dish.
 */
@RestController
@RequestMapping("/api")
public class DishResource {

    private final Logger log = LoggerFactory.getLogger(DishResource.class);

    @Inject
    private DishRepository dishRepository;

    @Inject
    private DishService dishService;

    /**
     * POST  /dishs -> Create a new dish.
     */
    @RequestMapping(value = "/dishs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Dish> createDish(@RequestBody Dish dish) throws URISyntaxException {
        log.debug("REST request to save Dish : {}", dish);
        if (dish.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dish", "idexists", "A new dish cannot already have an ID")).body(null);
        }
        Dish result = dishRepository.save(dish);
        return ResponseEntity.created(new URI("/api/dishs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dish", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dishs -> Updates an existing dish.
     */
    @RequestMapping(value = "/dishs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Dish> updateDish(@RequestBody Dish dish) throws URISyntaxException {
        log.debug("REST request to update Dish : {}", dish);
        if (dish.getId() == null) {
            return createDish(dish);
        }
        Dish result = dishRepository.save(dish);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dish", dish.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dishs -> get all the dishs.
     */
    @RequestMapping(value = "/dishs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DishDTO> getAllDishs() throws  Exception{
        log.debug("REST request to get all Dishs");
        List<DishDTO> dishDTOs = dishService.findAll();
        return dishDTOs;
     }

    /**
     * GET  /dishs/:id -> get the "id" dish.
     */
    @RequestMapping(value = "/dishs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DishDTO> getDish(@PathVariable String id)  throws Exception{
        log.debug("REST request to get Dish : {}", id);
        DishDTO dishDTO = dishService.findOne(id);
        if(dishDTO != null){
            return new ResponseEntity<DishDTO>(dishDTO,HttpStatus.OK);
        }else{
            return  new ResponseEntity<DishDTO>(dishDTO,HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET  /dishs/searchCondition
     */
    @RequestMapping(value = "/dishs/seacher",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DishDTO>> getDishBySearchName(@RequestParam(value = "name",required = true) String name)  throws Exception{
        log.debug("REST request to get Dish : {}", name);
        List<DishDTO> dishDTO = dishService.findByName(name);
        if(dishDTO != null){
            return new ResponseEntity<List<DishDTO>>(dishDTO,HttpStatus.OK);
        }else{
            return  new ResponseEntity<List<DishDTO>>(dishDTO,HttpStatus.NOT_FOUND);
        }
    }

    /**
     * DELETE  /dishs/:id -> delete the "id" dish.
     */
    @RequestMapping(value = "/dishs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDish(@PathVariable String id) {
        log.debug("REST request to delete Dish : {}", id);
        dishRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dish", id.toString())).build();
    }
}
