package com.github.huzhengen;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @RequestMapping("/search")
    public String getUserBlog(@RequestParam("q") String searchKeyword) {
        return "you are searching " + searchKeyword;
    }
}
