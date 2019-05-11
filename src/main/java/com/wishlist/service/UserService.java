package com.wishlist.service;

import com.wishlist.exception.NotFoundException;
import com.wishlist.model.User;
import com.wishlist.web.request.NewUserRequest;

public interface UserService {

    User findById(Long id) throws NotFoundException;
    User createUser(NewUserRequest request, boolean isActive);
    User updateUser(User user);
}
