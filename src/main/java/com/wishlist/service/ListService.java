package com.wishlist.service;

import com.wishlist.exception.NotFoundException;
import com.wishlist.model.List;
import com.wishlist.web.request.ListRequest;

public interface ListService {

    List save(ListRequest listRequest, Long userId) throws Exception;
    List update(ListRequest listRequest) throws Exception;
    void delete(Long listId);
    List findById(Long listId);
    List findOneByIdAndUserId(Long listId, Long userId) throws NotFoundException;
    java.util.List<List> findAllByUserId(Long userId);
}
