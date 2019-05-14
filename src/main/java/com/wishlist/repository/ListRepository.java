package com.wishlist.repository;

import com.wishlist.model.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListRepository extends JpaRepository<List, Long> {

    Optional<List> findOneByIdAndUserId(Long listId, Long userId);
    java.util.List<List> findAllByUserId(Long userId);
}
