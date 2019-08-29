package com.wishlist.service.impl;

import com.wishlist.model.Subscribition;
import com.wishlist.repository.SubscribitionRepository;
import com.wishlist.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {

    private SubscribitionRepository subscribitionRepository;

    @Autowired
    public EmailServiceImpl(SubscribitionRepository subscribitionRepository) {
        this.subscribitionRepository = subscribitionRepository;
    }

    @Override
    public Subscribition subscribe(Subscribition subscribition) {
        return subscribitionRepository.save(subscribition);
    }

    @Override
    public Subscribition unsubscribe(Subscribition subscribition) {
        return null;
    }
}
