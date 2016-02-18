package com.citygarden.repository;

import com.citygarden.domain.Announcement;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Announcement entity.
 */
public interface AnnouncementRepository extends MongoRepository<Announcement,String> {

}
