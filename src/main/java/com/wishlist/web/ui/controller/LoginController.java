package com.wishlist.web.ui.controller;

import com.wishlist.web.request.LoginRequest;
import com.wishlist.web.request.UserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @RequestMapping(path = "/login-form", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login/login";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "login/logout";
    }

    @RequestMapping(path = "/signin-form", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        return "/user/signin";
    }
}
