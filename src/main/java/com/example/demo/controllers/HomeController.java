package com.example.demo.controllers;

import com.example.demo.domain.Message;
import com.example.demo.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/greeting")
    public String home(@RequestParam(name = "name", required = false,
            defaultValue = "wold") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Model model) {
        model.addAttribute("messages", messageRepo.findAll());
        return "main";
    }

    @PostMapping
    public String add(
            @RequestParam String text,
            @RequestParam String tag,
            Model model) {
        Message message = new Message(text, tag);
        messageRepo.save(message);
        model.addAttribute("messages", messageRepo.findAll());
        return "main";
    }

    @PostMapping("/filter")
    public String filter(
            @RequestParam String filter,
            Model model) {
        if (filter != null && !filter.isEmpty()) {
            model.addAttribute("messages", messageRepo.findByTag(filter));
        } else {
            model.addAttribute("messages", messageRepo.findAll());
        }
        return "main";
    }
}
