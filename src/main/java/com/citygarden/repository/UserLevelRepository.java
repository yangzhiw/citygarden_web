package com.citygarden.repository;

import com.citygarden.domain.UserLevel;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Administrator on 2016/5/25 0025.
 */
public interface UserLevelRepository extends MongoRepository<UserLevel ,String> {
    UserLevel findByUserName(String username);
}
