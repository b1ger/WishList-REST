package com.wishlist.repository;

import com.wishlist.model.Subscribition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribitionRepository extends CrudRepository<Subscribition, Long> {

}
