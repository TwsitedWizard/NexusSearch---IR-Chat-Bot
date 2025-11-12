package com.nexussearch.nexussearch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// This tells Spring to ignore any extra fields in the JSON
@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleSearchItem(String title, String link) {
    // This 'record' is a modern Java way to create a
    // simple data class.
}