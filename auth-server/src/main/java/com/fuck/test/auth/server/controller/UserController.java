package com.fuck.test.auth.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * DESCRIPTION:
 *
 * @author zouyan
 * @create 2017-12-25 16:38
 * Created by fuck~ on 2017/12/25.
 */
@RestController
public class UserController {

    @GetMapping("/user")
    public Principal user(Principal user){
        return user;
    }
}

