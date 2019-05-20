package com.wishlist.repository;

import com.wishlist.model.List;
import com.wishlist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListRepository extends JpaRepository<List, Long> {

    Optional<List> findByIdAndUser(Long id, User user);
    java.util.List<List> findAllByUser(User user);
}
