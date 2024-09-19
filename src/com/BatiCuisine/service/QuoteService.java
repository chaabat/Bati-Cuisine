package com.BatiCuisine.service;

import com.BatiCuisine.model.Quote;
import com.BatiCuisine.repository.interfaces.QuoteRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class QuoteService {
    private final QuoteRepository quoteRepository;  // Inject the repository

    // Constructor to inject QuoteRepository dependency
    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    // Add a new quote (delegates to the repository)
    public void addQuote(Quote quote) {
        quoteRepository.addQuote(quote);
    }

    // Get a quote by UUID (returns Optional to handle the case where quote might not exist)
    public Optional<Quote> getQuoteById(UUID id) {
        return quoteRepository.getQuoteById(id);
    }

    // Get all quotes (returns a List of quotes)
    public List<Quote> getAllQuotes() {
        return quoteRepository.getAllQuotes();
    }

    // Calculate total estimated amount for a list of quotes
    public BigDecimal calculateTotalEstimatedAmount(List<Quote> quotes) {
        return quotes.stream()
                .map(Quote::getEstimatedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Calculate the acceptance rate of quotes
    public double calculateAcceptanceRate(List<Quote> quotes) {
        long totalQuotes = quotes.size();
        long acceptedQuotes = quotes.stream()
                .filter(Quote::isAccepted)
                .count();
        return totalQuotes > 0 ? (double) acceptedQuotes / totalQuotes : 0.0;
    }
}
