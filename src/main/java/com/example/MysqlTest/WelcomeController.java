package com.example.MysqlTest;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/hi")
    public String welcome(Model model){
        model.addAttribute( "username", "sam");
        return "greetings";
    }
}
