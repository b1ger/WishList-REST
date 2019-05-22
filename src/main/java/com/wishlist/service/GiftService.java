package com.wishlist.service;

import com.wishlist.model.Gift;

import java.util.List;
import java.util.Optional;

public interface GiftService {

    Gift save(Gift gift, Long giftId);
    Gift update(Gift gift);
    void delete(Gift gift);
    Gift findById(Long id);
    List<Gift> findAllByList(com.wishlist.model.List list);
}
