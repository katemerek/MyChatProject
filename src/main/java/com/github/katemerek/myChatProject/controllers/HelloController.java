package com.github.katemerek.myChatProject.controllers;

import com.github.katemerek.myChatProject.view.HelloApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HelloController {

    @GetMapping("/hello")
    public HelloApplication hello() {
        return HelloApplication.getInstance();
    }
}
