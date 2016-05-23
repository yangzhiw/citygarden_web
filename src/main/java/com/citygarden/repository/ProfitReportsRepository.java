package com.citygarden.repository;

import com.citygarden.domain.ProfitReports;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Administrator on 2016/5/23 0023.
 */
public interface ProfitReportsRepository extends MongoRepository<ProfitReports,String>{
    ProfitReports findByDishId(String id);
}
