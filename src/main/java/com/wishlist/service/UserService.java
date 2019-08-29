package com.wishlist.service;

import com.wishlist.exception.EmailAlreadyUsedException;
import com.wishlist.exception.NotFoundException;
import com.wishlist.model.User;
import com.wishlist.web.request.UserRequest;

public interface UserService {

    User findById(Long id) throws NotFoundException;
    User createUser(UserRequest request, boolean isActive) throws EmailAlreadyUsedException;
    User updateUser(User user);
}
