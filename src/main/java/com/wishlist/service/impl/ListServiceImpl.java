package com.wishlist.service.impl;

import com.wishlist.exception.NotFoundException;
import com.wishlist.model.List;
import com.wishlist.model.User;
import com.wishlist.repository.ListRepository;
import com.wishlist.repository.UserRepository;
import com.wishlist.service.ListService;
import com.wishlist.web.request.NewListRequest;
import com.wishlist.web.request.converter.ListConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List save(NewListRequest listRequest, Long userId) {
        if (listRequest == null) {
            log.error("NewListRequest can not be null.");
            throw new RuntimeException("Object NewListRequest can not be null.");
        }

        User user = userRepository.findOneById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id:$n, does not exist.", userId)));
        List list = listConverter.convert(listRequest);

        List savedList = listRepository.save(list);
        user.addList(savedList);
        userRepository.save(user);

        return savedList;
    }

    @Override
    public void delete(Long listId) {
        List deleted = listRepository.findById(listId)
                .orElseThrow(() -> new NotFoundException(String.format("List with id:$n, does not exist.", listId)));
        listRepository.delete(deleted);
    }

    @Override
    public List findById(Long listId) {
        return listRepository.findById(listId)
                .orElseThrow(() -> new NotFoundException(String.format("List with id:$n, does not exist.", listId)));
    }

    @Override
    public java.util.List<List> findAllByUserId(Long userId) {
        return listRepository.findAllByUserId(userId);
    }
}
