package com.github.huzhengen;

import com.github.huzhengen.entity.User;
import com.github.huzhengen.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@RestController
public class HelloController {
    private UserService userService;

    @Inject
    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public User index() {
        return this.userService.getUserById(1);
//        return "welcome index";
    }

    @RequestMapping("/search")
    public String getUserBlog(@RequestParam("q") String searchKeyword) {
        return "you are searching " + searchKeyword;
    }
}
