package com.wishlist.service;

import com.wishlist.model.Subscribition;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    Subscribition subscribe(Subscribition subscribition);
    Subscribition unsubscribe(Subscribition subscribition);
}
