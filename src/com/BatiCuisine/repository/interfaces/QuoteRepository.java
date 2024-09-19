package com.BatiCuisine.repository.interfaces;

import com.BatiCuisine.model.Quote;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuoteRepository {
    void addQuote(Quote quote);  // Add a new quote
    Optional<Quote> getQuoteById(UUID id);  // Retrieve a quote by its UUID
    List<Quote> getAllQuotes();  // Fetch all quotes
}
