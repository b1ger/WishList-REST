package com.wishlist.web.ui.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/index")
    public String indexAction() {
        return "admin/index";
    }
}
