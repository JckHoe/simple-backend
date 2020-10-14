package com.tribehired.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SimpleController {

    @GetMapping("/health")
    public String getHealth() {
        return "Im Alive";
    }
}
