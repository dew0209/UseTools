package com.example.demo.shiro.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShiroController {

    @RequestMapping("/shiro/index")
    public String shiroIndex(){
        return "/shiro/index";
    }
    @RequestMapping("/shiro/login")
    public String shiroLogin(){
        return "/shiro/login";
    }
    @RequestMapping("/shiro/list")
    public String shiroList(){
        return "/shiro/list";
    }
    @RequestMapping("/shiro/unauthorized")
    public String shiroUnauthorized(){
        return "/shiro/unauthorized";
    }
}
