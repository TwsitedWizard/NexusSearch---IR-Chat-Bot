package com.nexussearch.nexussearch.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.GoogleSearch;
import com.google.genai.types.Tool;
import com.nexussearch.nexussearch.dto.GoogleSearchResponse;
import com.nexussearch.nexussearch.dto.GoogleSearchItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeminiService {

    private final Client geminiClient;
    private final GenerateContentConfig groundingConfig;
    private final GoogleSearchService googleSearchService;

    @Autowired
    public GeminiService(GoogleSearchService googleSearchService) {
        this.googleSearchService = googleSearchService;
        this.geminiClient = new Client();
        Tool groundingTool = Tool.builder()
                .googleSearch(GoogleSearch.builder().build())
                .build();
        this.groundingConfig = GenerateContentConfig.builder()
                .tools(Collections.singletonList(groundingTool))
                .build();
    }

    public String generateContent(String userQuery) throws IOException {
        
        // --- THIS IS THE NEW "TWO-CALL" LOGIC ---
        
        // NEW: Check for image intent first
        String lowerQuery = userQuery.toLowerCase();
        boolean isImageQuery = lowerQuery.contains("picture") || 
                               lowerQuery.contains("image") || 
                               lowerQuery.contains("show me");

        GoogleSearchResponse searchResponse = null; // Initialize as null
        
        if (!isImageQuery) {
            // 1. If it's NOT an image query, try to get real links
            searchResponse = googleSearchService.search(userQuery);
        }
        // If it IS an image query, we skip this. 'searchResponse' stays null,
        // which forces the app into the "else" (fallback) block below.

        String fullPrompt;

        if (searchResponse != null && searchResponse.items() != null && !searchResponse.items().isEmpty()) {
            // SUCCESS! We got real links (for a non-image query).
            System.out.println("--- Using Google Search API links ---");

            String searchResults = searchResponse.items().stream()
                .map(item -> String.format("- Title: %s, URL: %s", item.title(), item.link()))
                .collect(Collectors.joining("\n"));

            fullPrompt = "You are 'NexusSearch', an AI-powered conversational search assistant. "
                + "A user asked: '" + userQuery + "'. "
                + "I have performed a Google search and found these real-time, verified results:\n"
                + searchResults + "\n\n"
                + "Your job is to answer the user's query *only* using these results. "
                + "Summarize the findings and **you MUST use the exact URLs provided** when you include links. "
                + "Format your answer in Markdown.";

        } else {
            // FAILURE or FALLBACK (now also handles all image queries)
            if(isImageQuery) {
                System.out.println("--- Image query detected, falling back to Gemini-only ---");
            } else {
                System.out.println("--- Google Search failed, falling back to Gemini-only ---");
            }
            
            // This is our old, reliable prompt that's good at images
            fullPrompt = "You are 'NexusSearch', an AI-powered conversational search assistant. "
                + "You MUST use your Google Search tool. "
                + "You MUST answer in Markdown. "
                + "**CRITICAL RULE:** When you provide links, you MUST provide the raw, original, direct URL to the source. "
                + "DO NOT use 'google.com' redirect links. "
                + "**NEW RULE:** You must only provide links that you have **verified are active and real**. "
                + "If you are not 100% sure of the *exact* item URL, it is **better to provide a link to the website's search results page** instead of a broken, non-existent item link. "
                + "For images, use Markdown `![alt](direct_url.jpg)`. "
                + "\n\nUser Query: " + userQuery;
        }

        // 2. Second, call Gemini with our chosen prompt
        try {
            String modelId = "gemini-2.5-flash";
            
            GenerateContentResponse response = this.geminiClient.models.generateContent(
                    modelId,
                    fullPrompt,
                    this.groundingConfig
            );

            return response.text();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to get a response from the AI. " + e.getMessage();
        }
    }
}