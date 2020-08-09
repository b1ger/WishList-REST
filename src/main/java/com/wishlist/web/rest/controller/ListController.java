package com.wishlist.web.rest.controller;

import com.wishlist.model.List;
import com.wishlist.service.ListService;
import com.wishlist.web.request.ListRequest;
import com.wishlist.web.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class ListController {

    private final ListService listService;

    @Autowired
    public ListController(ListService listService) {
        this.listService = listService;
    }

    @PostMapping(value = "/rest/{userId}/list",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<BaseResponse> newList(@Valid @RequestBody ListRequest listRequest,
                                                @PathVariable String userId,
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
            List saved = listService.save(listRequest, Long.valueOf(userId));
            response.setResults(saved, BaseResponse.OK_STATUS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            response.setResults(ex.getMessage(), BaseResponse.ERROR_STATUS);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = {"/rest/{userId}/list/{listId}", "/rest/{userId}/list"},
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<BaseResponse> getList(@PathVariable String userId, @PathVariable(required = false) String listId) {
        BaseResponse response = new BaseResponse();
        try {
            if (StringUtils.isEmpty(listId)) {
                java.util.List<List> userLists = listService.findAllByUserId(Long.valueOf(userId));
                response.setResults(userLists, BaseResponse.OK_STATUS);
            } else {
                List list = listService.findOneByIdAndUserId(
                        Long.valueOf(listId),
                        Long.valueOf(userId)
                );
                response.setResults(list, BaseResponse.OK_STATUS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            response.setResults(ex.getMessage(), BaseResponse.ERROR_STATUS);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/rest/{userId}/list/{listId}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<BaseResponse> updateList(@Valid @RequestBody ListRequest listRequest,
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
            List saved = listService.update(listRequest);
            response.setResults(saved, BaseResponse.OK_STATUS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            response.setResults(ex.getMessage(), BaseResponse.ERROR_STATUS);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/rest/{userId}/list/{listId}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<BaseResponse> deleteList(@PathVariable String userId,
                                                   @PathVariable String listId
    ) {
        BaseResponse response = new BaseResponse();
        listService.delete(Long.valueOf(listId));
        response.setResults("List was deleted.", BaseResponse.OK_STATUS);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
