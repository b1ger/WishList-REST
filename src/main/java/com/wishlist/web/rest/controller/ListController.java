package com.wishlist.web.rest.controller;

import com.wishlist.model.List;
import com.wishlist.service.ListService;
import com.wishlist.web.request.NewListRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class ListController {

    private ListService listService;

    @Autowired
    public ListController(ListService listService) {
        this.listService = listService;
    }

    @PostMapping(value = "/rest/{userId}/list",
            consumes = "application/json",
            produces = "application/json")
    public List newList(@Valid @RequestBody NewListRequest listRequest,
                        @PathVariable String userId,
                        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.error(objectError.toString());
            });
        }
        return listService.save(listRequest, Long.valueOf(userId));
    }
}
