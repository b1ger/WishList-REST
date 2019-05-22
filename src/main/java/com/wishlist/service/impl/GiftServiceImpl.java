package com.wishlist.service.impl;

import com.wishlist.exception.NotFoundException;
import com.wishlist.model.Gift;
import com.wishlist.model.User;
import com.wishlist.repository.GiftRepository;
import com.wishlist.service.GiftService;
import com.wishlist.service.ListService;
import com.wishlist.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GiftServiceImpl implements GiftService {

    private GiftRepository giftRepository;
    private ListService listService;

    @Autowired
    public GiftServiceImpl(GiftRepository giftRepository, ListService listService) {
        this.giftRepository = giftRepository;
        this.listService = listService;
    }

    @Override
    public Gift save(Gift gift, Long listId) throws NotFoundException {
        com.wishlist.model.List list = listService.findById(listId);
        gift.setList(list);
        Gift saved = giftRepository.save(gift);
        log.debug("Save new gift {}, to list with id: {}", saved, listId);
        return saved;
    }

    @Override
    public Gift update(Gift gift) {
        return null;
    }

    @Override
    public void delete(Gift gift) {
        giftRepository.delete(gift);
    }

    @Override
    public Gift findById(Long id) {
        Optional<Gift> optional = giftRepository.findById(id);
        return optional.orElseThrow(() -> new NotFoundException(String.format("Gift with id:$n, does not exist.", id)));
    }

    @Override
    public List<Gift> findAllByList(com.wishlist.model.List list) {
        return giftRepository.findAllByList(list);
    }
}
