package com.wishlist.web.ui.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class IndexController {

    @RequestMapping("/index")
    public String indexAction() {
        return "index";
    }
}
