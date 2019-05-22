package com.wishlist.web.rest.controller;

import com.wishlist.model.Gift;
import com.wishlist.service.GiftService;
import com.wishlist.web.rest.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class GiftController {

    private GiftService giftService;

    @Autowired
    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    @PostMapping(value = "/rest/{userId}/list/{listId}/gift",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<BaseResponse> saveGift(
            @Valid @RequestBody Gift gift,
            @PathVariable String userId,
            @PathVariable String listId,
            BindingResult bindingResult
    ) {
        BaseResponse response = new BaseResponse();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.error(objectError.toString());
            });
            response.setResults(bindingResult.getAllErrors(), BaseResponse.ERROR_STATUS);
        }
        try {
            Gift saved = giftService.save(gift, Long.valueOf(listId));
            response.setResults(saved, BaseResponse.OK_STATUS);
        } catch (Exception ex) {
            response.setResults(ex.getMessage(), BaseResponse.ERROR_STATUS);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
