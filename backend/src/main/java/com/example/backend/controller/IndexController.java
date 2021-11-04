package com.example.backend.controller;

import com.example.backend.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){
        String s = null;
        if(s==null){
            throw new NotFoundException("can not find blog");
        }
        return "index";
    }
}
