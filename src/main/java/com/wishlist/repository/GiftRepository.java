package com.wishlist.repository;

import com.wishlist.model.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {

    List<Gift> findAllByList(com.wishlist.model.List list);
}
