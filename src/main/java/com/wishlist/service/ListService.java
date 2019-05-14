package com.wishlist.service;

import com.wishlist.model.List;
import com.wishlist.web.request.NewListRequest;

public interface ListService {

    List save(NewListRequest listRequest, Long userId);
    void delete(Long listId);
    List findById(Long listId);
    java.util.List<List> findAllByUserId(Long userId);
}
