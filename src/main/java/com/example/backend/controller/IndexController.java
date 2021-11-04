package com.example.backend.controller;

import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
