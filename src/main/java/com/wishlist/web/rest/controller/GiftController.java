package com.wishlist.web.rest.controller;

import com.wishlist.model.Gift;
import com.wishlist.service.GiftService;
import com.wishlist.web.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/apiwl")
public class GiftController {

    private GiftService giftService;

    @Autowired
    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    @PostMapping(value = "/list/{listId}/gift",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<BaseResponse> saveGift(
            @Valid @RequestBody Gift gift,
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
            log.error(ex.getMessage());
            response.setResults(ex.getMessage(), BaseResponse.ERROR_STATUS);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/gift/update",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<BaseResponse> updateGift(
            @Valid @RequestBody Gift gift,
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
            Gift saved = giftService.update(gift);
            response.setResults(saved, BaseResponse.OK_STATUS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            response.setResults(ex.getMessage(), BaseResponse.ERROR_STATUS);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/gift/{giftId}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<BaseResponse> deleteGift(@PathVariable String giftId) {
        BaseResponse response = new BaseResponse();
        giftService.delete(giftService.findById(Long.valueOf(giftId)));
        response.setResults("Gift was deleted.", BaseResponse.OK_STATUS);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
