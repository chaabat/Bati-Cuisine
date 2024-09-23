package com.BatiCuisine.service;


import com.BatiCuisine.model.Quote;

import com.BatiCuisine.repository.interfaces.QuoteRepository;
import com.BatiCuisine.repository.interfaces.ProjectRepository;


public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final ProjectRepository projectRepository;

    // Constructor to inject QuoteRepository and ProjectRepository dependencies
    public QuoteService(QuoteRepository quoteRepository, ProjectRepository projectRepository) {
        this.quoteRepository = quoteRepository;
        this.projectRepository = projectRepository;
    }

    // Add a new quote (delegates to the repository)
    public void addQuote(Quote quote) {
        quoteRepository.addQuote(quote);
    }


}
