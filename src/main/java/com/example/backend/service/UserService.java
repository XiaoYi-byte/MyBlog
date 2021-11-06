package com.example.backend.service;

import com.example.backend.entity.User;
import org.springframework.stereotype.Service;


public interface UserService {

    User checkUser(String username,String password);
}
