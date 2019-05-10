package com.wishlist.web.ui.controller.admin;

import com.wishlist.model.Authority;
import com.wishlist.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/authority")
public class AuthorityController {

    private AuthorityService authorityService;

    @Autowired
    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @RequestMapping("/index")
    public String indexAuthority(Model model) {
        List<Authority> authorities = authorityService.getAuthorityList();
        model.addAttribute("authorities", authorities);
        return "index";
    }

    @RequestMapping("/create")
    public String createAuthority(Model model) {
        Authority authority = new Authority();
        model.addAttribute("authority", authority);
        return "create";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveAuthority(@Valid @ModelAttribute("authority") Authority authority,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.warn(objectError.toString());
            });
        }

        Authority saved = authorityService.save(authority);
        log.debug("Saved authority: " + saved);

        return "forward:/index";
    }

    @RequestMapping("/delete/{name}")
    public String deleteAction(@PathVariable("name") String name) {
        Authority authority = authorityService.getAuthorityByName(name);
        authorityService.delete(authority);
        return "forward:/index";
    }
}
