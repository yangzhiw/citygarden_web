package com.citygarden.web.rest;

import com.citygarden.domain.Dish;
import com.codahale.metrics.annotation.Timed;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/24 0024.
 */
@RestController
@RequestMapping("/api")
public class DishPhotoResource {
    private final Logger log = LoggerFactory.getLogger(DishPhotoResource.class);

    @Inject
    private Environment env;

    /**
     * GET  /dishs -> get all the dishs.
     */
    @RequestMapping(value = "/dishphoto",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Map getAllDishPhoto() throws  Exception{
        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("citygarden_web");
        log.debug("REST request to get all Dishs");
        String newFileName = "bocai";
        GridFS gfsPhoto = new GridFS(db, "T_DIST_PHOTO");
        GridFSDBFile imageForOutput = gfsPhoto.findOne(newFileName);
        System.out.println(imageForOutput);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        imageForOutput.writeTo(bos);
        Map map=new HashMap<String,String>();
        map.put("photo",DatatypeConverter.printBase64Binary(bos.toByteArray()));
        return map;
    }
}
