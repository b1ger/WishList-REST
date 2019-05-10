package com.wishlist.service;

import com.wishlist.model.Authority;

import java.util.List;

public interface AuthorityService {

    List<Authority> getAuthorityList();
    Authority getAuthorityByName(String name);
    Authority save(Authority authority);
    void delete(Authority authority);
}
