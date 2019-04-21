package com.wardrobe.repository;

import com.wardrobe.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserRepository extends CrudRepository<User, Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);
}
