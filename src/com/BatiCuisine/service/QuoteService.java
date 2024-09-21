package com.BatiCuisine.service;

import com.BatiCuisine.model.ProjectStatus;
import com.BatiCuisine.model.Quote;
import com.BatiCuisine.model.Project;
import com.BatiCuisine.repository.interfaces.QuoteRepository;
import com.BatiCuisine.repository.interfaces.ProjectRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class QuoteService {
    private QuoteRepository quoteRepository;  // Inject the repository
    private ProjectRepository projectRepository; // Inject the project repository

    // Constructor to inject QuoteRepository and ProjectRepository dependencies
    public QuoteService(QuoteRepository quoteRepository, ProjectRepository projectRepository) {
        this.quoteRepository = quoteRepository;
        this.projectRepository = projectRepository;
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

    // Handle acceptance or decline of a quote
    public void handleQuoteDecision(UUID quoteId, boolean accept) {
        Optional<Quote> quoteOpt = quoteRepository.getQuoteById(quoteId);
        if (quoteOpt.isPresent()) {
            Quote quote = quoteOpt.get();
            quote.setAccepted(accept);

            // Update the project status based on the decision
            Project project = quote.getProject(); // Assuming the quote has a reference to the project
            if (accept) {
                project.setProjectStatus(ProjectStatus.COMPLETED);
                System.out.println("Devis accepté. Le statut du projet a été mis à jour en 'COMPLETED'.");
            } else {
                project.setProjectStatus(ProjectStatus.CANCELLED);
                System.out.println("Devis refusé. Le statut du projet a été mis à jour en 'CANCELLED'.");
            }

            // Save the updated project status
            projectRepository.updateProject(project); // Make sure you have this method in your ProjectRepository
        } else {
            System.out.println("Devis introuvable avec cet identifiant.");
        }
    }

    // List all quotes
    public List<Quote> listAllQuotes() {
        return quoteRepository.getAllQuotes();
    }
}
