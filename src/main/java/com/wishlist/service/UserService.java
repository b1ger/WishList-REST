package com.wishlist.service;

import com.wishlist.exception.NotFoundException;
import com.wishlist.model.User;
import com.wishlist.web.request.UserRequest;

public interface UserService {

    User findById(Long id) throws NotFoundException;
    User createUser(UserRequest request, boolean isActive);
    User updateUser(User user);
}
