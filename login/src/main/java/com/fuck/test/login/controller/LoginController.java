package com.fuck.test.login.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @RequestMapping("/login")
    public Map<String, Object> test(@RequestParam("username") String username, @RequestParam("password") String password) {
        logger.info("username:{}, password:{}", username, password);
        Map<String, Object> result = new HashMap<String, Object>() {
            {
                put("userId", username + "-uid");
            }
        };
        logger.info("result:{}", result);
        return result;
    }

}
