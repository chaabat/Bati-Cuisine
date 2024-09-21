package com.BatiCuisine.repository.interfaces;

import com.BatiCuisine.model.Quote;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuoteRepository {
    // Add a new quote
    void addQuote(Quote quote);

    // Get a quote by its UUID
    Optional<Quote> getQuoteById(UUID id);

    // Get all quotes
    List<Quote> getAllQuotes();

    // Update a quote's accepted status
    void updateQuote(Quote quote);

    // Get quotes by project ID
    List<Quote> getQuotesByProjectId(UUID projectId);
}
