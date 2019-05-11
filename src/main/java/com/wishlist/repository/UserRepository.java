package com.wishlist.repository;

import com.wishlist.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);
    Optional<User> findOneByEmailIgnoreCase(String email);
    Optional<User> findById(Long id);
}
