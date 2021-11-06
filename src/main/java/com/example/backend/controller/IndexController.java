package com.example.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){
//        String s = null;
//        if(s==null){
//            throw new NotFoundException("can not find blog");
//        }
        return "index";
    }

    @GetMapping("/blog")
    public String blog(){
        return "blog";
    }

}
