package com.nexussearch.nexussearch.controller;

import com.nexussearch.nexussearch.service.GeminiService; // No more DTO import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChatController {

    private final GeminiService geminiService;

    @Autowired
    public ChatController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/")
    public String chatPage() {
        return "chat";
    }

    @PostMapping("/chat")
    @ResponseBody
    // Return a simple String
    public String handleChatQuery(@RequestBody String query) {
        try {
            return geminiService.generateContent(query);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}