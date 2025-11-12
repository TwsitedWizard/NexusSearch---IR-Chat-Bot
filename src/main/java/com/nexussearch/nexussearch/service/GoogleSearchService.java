package com.nexussearch.nexussearch.service;

import com.nexussearch.nexussearch.dto.GoogleSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleSearchService {

    // Spring will automatically find these from the environment variables
    @Value("${GOOGLE_SEARCH_API_KEY}")
    private String apiKey;

    @Value("${GOOGLE_SEARCH_ENGINE_ID}")
    private String searchEngineId;

    private final RestTemplate restTemplate;

    public GoogleSearchService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Searches the Google Custom Search API for a query.
     * @param query The user's search query.
     * @return A GoogleSearchResponse object or null if an error occurs.
     */
    public GoogleSearchResponse search(String query) {
        try {
            // Build the URL for the Google Search API
            String url = String.format(
                "https://www.googleapis.com/customsearch/v1?key=%s&cx=%s&q=%s&num=5",
                apiKey, searchEngineId, query
            );

            // Call the API and map the JSON to our DTO
            return restTemplate.getForObject(url, GoogleSearchResponse.class);

        } catch (Exception e) {
            System.err.println("Error calling Google Search API: " + e.getMessage());
            // Return null if the search fails (e.g., API limit reached)
            return null;
        }
    }
}