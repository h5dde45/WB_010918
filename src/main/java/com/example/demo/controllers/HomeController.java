package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @GetMapping("/greeting")
    public String home(@RequestParam(name = "name", required = false,
            defaultValue = "wold") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
    @GetMapping
    public String main(Model model) {
        model.addAttribute("some", " something");
        return "main";
    }
}
