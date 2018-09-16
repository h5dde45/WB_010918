package com.example.demo.controllers;

import com.example.demo.domain.Message;
import com.example.demo.domain.User;
import com.example.demo.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

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
            Model model,
            @PageableDefault(sort = "id", size = 5, direction = Sort.Direction.DESC) Pageable pageable) {
        if (filter != null && !filter.isEmpty()) {
            model.addAttribute("page", messageRepo.findByTag(filter, pageable));
        } else {
            model.addAttribute("page", messageRepo.findAll(pageable));
        }

        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);
        return "main";
    }

    @GetMapping("/img/{id}")
    public ResponseEntity<byte[]> image(@PathVariable("id") Message message) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(message.getImage(), headers, HttpStatus.CREATED);
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {
        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                message.setImage(file.getBytes());
            }
            model.addAttribute("message", null);
            messageRepo.save(message);
        }
        model.addAttribute("messages", messageRepo.findAll());
        return "main";
    }

    @GetMapping("/user-messages/{user}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Message message,
            @PageableDefault(sort = "id", size = 5, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Message> messages = messageRepo.findByAuthor(user, pageable);

        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("url", "/user-messages/"+user.getId());
        model.addAttribute("page", messages);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Message message,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            }

            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }

            if (file != null && !file.getOriginalFilename().isEmpty()) {
                message.setImage(file.getBytes());
            }

            messageRepo.save(message);
        }

        return "redirect:/user-messages/" + user;
    }
}
