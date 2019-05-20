package com.wishlist.service.impl;

import com.wishlist.exception.NotFoundException;
import com.wishlist.model.List;
import com.wishlist.model.User;
import com.wishlist.repository.ListRepository;
import com.wishlist.repository.UserRepository;
import com.wishlist.service.ListService;
import com.wishlist.web.request.ListRequest;
import com.wishlist.web.request.converter.ListConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class ListServiceImpl implements ListService {

    private ListRepository listRepository;
    private UserRepository userRepository;
    private ListConverter listConverter;

    @Autowired
    public ListServiceImpl(ListRepository listRepository, UserRepository userRepository, ListConverter listConverter) {
        this.listRepository = listRepository;
        this.userRepository = userRepository;
        this.listConverter = listConverter;
    }

    @Override
    public List save(ListRequest listRequest, Long userId) throws RuntimeException {
        if (listRequest == null) {
            log.error("NewListRequest can not be null.");
            throw new RuntimeException("Object NewListRequest can not be null.");
        }

        User user = findUser(userId);
        List list = listConverter.convert(listRequest);

        List savedList = listRepository.save(list);
        user.addList(savedList);
        userRepository.save(user);

        return savedList;
    }

    @Override
    public List update(ListRequest listRequest) throws Exception {
        List list = listConverter.convert(listRequest);
        List detached = listRepository.findById(Objects.requireNonNull(list).getId())
                .orElseThrow(() -> new NotFoundException(String.format("List with id:$n, does not exist.", list.getId())));
        list.setUser(detached.getUser());
        list.setGifts(detached.getGifts());
        List saved = listRepository.save(list);
        log.info("List was updated: " + saved);
        return saved;
    }

    @Override
    public void delete(Long listId) throws NotFoundException {
        List deleted = listRepository.findById(listId)
                .orElseThrow(() -> new NotFoundException(String.format("List with id:$n, does not exist.", listId)));
        listRepository.delete(deleted);
    }

    @Override
    public List findById(Long listId) throws NotFoundException {
        return listRepository.findById(listId)
                .orElseThrow(() -> new NotFoundException(String.format("List with id:$n, does not exist.", listId)));
    }

    @Override
    public List findOneByIdAndUserId(Long listId, Long userId) throws NotFoundException {
        User user = findUser(userId);
        return listRepository.findByIdAndUser(listId, user)
                .orElseThrow(() -> new NotFoundException(String.format("List with id:$n, does not exist for user with id:$n", listId, userId)));
    }

    @Override
    public java.util.List<List> findAllByUserId(Long userId) {
        User user = findUser(userId);
        return listRepository.findAllByUser(user);
    }

    private User findUser(Long userId) {
        return userRepository.findOneById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id:$n, does not exist.", userId)));
    }
}
