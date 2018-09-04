package com.example.demo.controllers;

import com.example.demo.domain.Message;
import com.example.demo.domain.User;
import com.example.demo.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(
            @RequestParam(required = false) String filter,
            Model model) {
        if (filter != null && !filter.isEmpty()) {
            model.addAttribute("messages", messageRepo.findByTag(filter));
        } else {
            model.addAttribute("messages", messageRepo.findAll());
        }
        model.addAttribute("filter", filter);
        return "main";
    }


    @GetMapping("/img/{id}")
    public ResponseEntity<byte[]> image(@PathVariable("id") Message message) throws IOException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(message.getImage(), headers, HttpStatus.CREATED);
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {
        Message message = new Message(text, tag, user);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            message.setImage(file.getBytes());
        }

        messageRepo.save(message);
        model.addAttribute("messages", messageRepo.findAll());
        return "main";
    }
}
