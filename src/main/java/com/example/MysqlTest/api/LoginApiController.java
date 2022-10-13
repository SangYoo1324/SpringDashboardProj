package com.example.MysqlTest.api;

import com.example.MysqlTest.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginApiController {
    @Autowired
    LoginRepository loginRepository;

    @PostMapping("/api/generateId")
    public void generateId(){


    }
}
