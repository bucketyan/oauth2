package com.fuck.test.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class DemoController {

    @RequestMapping("/test")
    public String test(@RequestParam("id") String id) {
        return id;
    }

    @RequestMapping("/test2")
    public String test2(HttpServletRequest request) {
        return "ok";
    }

}
