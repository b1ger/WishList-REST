package com.wishlist.web.ui.controller.admin;

import com.wishlist.model.User;
import com.wishlist.service.UserService;
import com.wishlist.web.request.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/admin/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createAction(Model model) {
        UserRequest userRequest = new UserRequest();
        model.addAttribute("user", userRequest);
        return "admin/user/create";
    }

    @PostMapping("/save")
    public String saveAction(@Valid @ModelAttribute("user") UserRequest userRequest,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "admin/user/create";
        }
        User user = userService.createUser(userRequest, false);

        return "admin/user/index";
    }
}
