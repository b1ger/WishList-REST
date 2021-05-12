package com.wishlist.service.impl;

import com.wishlist.exception.NotFoundException;
import com.wishlist.model.Authority;
import com.wishlist.repository.AuthorityRepository;
import com.wishlist.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<Authority> getAuthorityList() {
        return authorityRepository.findAllByOrderByName();
    }

    @Override
    public Authority getAuthorityByName(String name) {
        return authorityRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("Authority with name \"%s\" does not exist", name)));
    }

    @Override
    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    public void delete(Authority authority) {
        authorityRepository.delete(authority);
    }
}
