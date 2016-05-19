package com.citygarden.service;

import com.citygarden.repository.ProvideMerchantRepository;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by yzw on 2016/5/18 0018.
 */

@Service
public class ProvideMerchantPhotoService {
    private final Logger log = LoggerFactory.getLogger(ProvideMerchantPhotoService.class);

    @Inject
    private Environment env;

    @Inject
    private MongoDbFactory mongoDbFactory;

    @Inject
    private MongoTemplate mongoTemplate;

    @Inject
    private ProvideMerchantRepository provideMerchantRepository;

    public String getProvidePhoto(String name) throws  Exception{
        Mongo mongo = mongoDbFactory.getDb().getMongo();
        DB db = mongo.getDB(env.getProperty("spring.data.mongodb.database"));
        log.debug("REST request to get providePhoto");;
        GridFS gfsPhoto = new GridFS(db, "T_PROVIDE_PHOTO");
        GridFSDBFile imageForOutput = gfsPhoto.findOne(name);
        System.out.println(imageForOutput);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if(imageForOutput != null){
            imageForOutput.writeTo(bos);
        }
        return DatatypeConverter.printBase64Binary(bos.toByteArray());
    }

    public void importPhoto(InputStream br, String pid) {
        Mongo mongo = mongoDbFactory.getDb().getMongo();
        DB db = mongo.getDB(env.getProperty("spring.data.mongodb.database"));
        log.debug("REST request to post providePhoto");
        GridFS gfsPhoto = new GridFS(db, "T_PROVIDE_PHOTO");

        String pname = provideMerchantRepository.findOne(pid).getName();

        // get image file from local drive
        GridFSInputFile gfsFile = gfsPhoto.createFile(br);

        // set a new filename for identify purpose
        gfsFile.setFilename(pname);

        // save the image file into mongoDB
        gfsFile.save();

        DBCursor cursor = gfsPhoto.getFileList();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
//
    }
}
