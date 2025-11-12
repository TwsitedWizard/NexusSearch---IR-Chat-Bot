package com.nexussearch.nexussearch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleSearchResponse(List<GoogleSearchItem> items) {
    // This will automatically map the "items" array
    // in the JSON to a List of our GoogleSearchItem records.
}