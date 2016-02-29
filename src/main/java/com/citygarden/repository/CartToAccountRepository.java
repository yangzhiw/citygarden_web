package com.citygarden.repository;


import com.citygarden.domain.CartToAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Administrator on 2016/2/29 0029.
 */
public interface CartToAccountRepository  extends MongoRepository<CartToAccount,String> {

    CartToAccount findByOrderStatusAndUsername(String s, String id);
}
