package com.tribehired.controller;

import com.tribehired.integration.service.SinglePostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SimpleController {
    private final SinglePostService singlePostService;

    @GetMapping("/health")
    public String getHealth() {
        singlePostService.getSinglePost("1");
        return "Im Alive";
    }
}
