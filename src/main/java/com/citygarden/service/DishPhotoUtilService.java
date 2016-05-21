package com.citygarden.service;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Service;


import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2016/2/24 0024.
 */
@Service
public class DishPhotoUtilService {
    private final Logger log = LoggerFactory.getLogger(DishPhotoUtilService.class);

    @Inject
    private Environment env;

    @Inject
    private MongoDbFactory mongoDbFactory;

    public String getDishPhoto(String name) throws  Exception{
        Mongo mongo = mongoDbFactory.getDb().getMongo();
        DB db = mongo.getDB(env.getProperty("spring.data.mongodb.database"));
        log.debug("REST request to get dishPhoto");;
        GridFS gfsPhoto = new GridFS(db, "T_DISH_PHOTO");
        GridFSDBFile imageForOutput = gfsPhoto.findOne(name);
        System.out.println(imageForOutput);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        imageForOutput.writeTo(bos);
        return DatatypeConverter.printBase64Binary(bos.toByteArray());
    }
}
