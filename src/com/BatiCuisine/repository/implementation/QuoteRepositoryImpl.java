package com.BatiCuisine.repository.implementation;

import com.BatiCuisine.model.Quote;
import com.BatiCuisine.model.Project;
import com.BatiCuisine.repository.interfaces.QuoteRepository;
import com.BatiCuisine.repository.interfaces.ProjectRepository; // Import ProjectRepository
import com.BatiCuisine.config.DataBaseConnection;

import java.math.BigDecimal; // Import BigDecimal
import java.sql.*;
import java.util.*;

public class QuoteRepositoryImpl implements QuoteRepository {
    private final Map<UUID, Quote> quoteCache = new HashMap<>();

    @Override
    public void addQuote(Quote quote) {
        String query = "INSERT INTO quotes (id, estimatedAmount, issueDate, validityDate, isAccepted, projectId) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            UUID quoteId = quote.getId() != null ? quote.getId() : UUID.randomUUID();
            quote.setId(quoteId);

            statement.setObject(1, quoteId, Types.OTHER);
            statement.setBigDecimal(2, quote.getEstimatedAmount());
            statement.setDate(3, java.sql.Date.valueOf(quote.getIssueDate())); // Use java.sql.Date
            statement.setDate(4, java.sql.Date.valueOf(quote.getValidityDate())); // Use java.sql.Date
            statement.setBoolean(5, quote.isAccepted());
            statement.setObject(6, quote.getProject().getId(), Types.OTHER);

            statement.executeUpdate();
            quoteCache.put(quoteId, quote);

        } catch (SQLException e) {
            System.err.println("Error adding quote to the database. Details: " + e.getMessage());
        }
    }

    @Override
    public Optional<Quote> getQuoteById(UUID id) {
        if (quoteCache.containsKey(id)) {
            return Optional.of(quoteCache.get(id));
        }

        String query = "SELECT * FROM quotes WHERE id = ?";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, id, Types.OTHER);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Quote quote = createQuoteFromResultSet(resultSet);
                quoteCache.put(quote.getId(), quote);
                return Optional.of(quote);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching quote by ID. Details: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Quote> getAllQuotes() {
        List<Quote> quotes = new ArrayList<>();
        String query = "SELECT * FROM quotes";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Quote quote = createQuoteFromResultSet(resultSet);
                quoteCache.put(quote.getId(), quote);
                quotes.add(quote);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all quotes. Details: " + e.getMessage());
        }
        return quotes;
    }

    @Override
    public void updateQuote(Quote quote) {
        String query = "UPDATE quotes SET isAccepted = ? WHERE id = ?";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setBoolean(1, quote.isAccepted());
            statement.setObject(2, quote.getId(), Types.OTHER);
            statement.executeUpdate();

            quoteCache.put(quote.getId(), quote);

        } catch (SQLException e) {
            System.err.println("Error updating quote. Details: " + e.getMessage());
        }
    }

    @Override
    public List<Quote> getQuotesByProjectId(UUID projectId) {
        List<Quote> quotes = new ArrayList<>();
        String query = "SELECT * FROM quotes WHERE projectId = ?";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, projectId, Types.OTHER);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Quote quote = createQuoteFromResultSet(resultSet);
                quoteCache.put(quote.getId(), quote);
                quotes.add(quote);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching quotes by project ID. Details: " + e.getMessage());
        }
        return quotes;
    }

    private Quote createQuoteFromResultSet(ResultSet rs) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        BigDecimal estimatedAmount = rs.getBigDecimal("estimatedAmount");
        java.sql.Date issueDate = rs.getDate("issueDate"); // Use java.sql.Date
        java.sql.Date validityDate = rs.getDate("validityDate"); // Use java.sql.Date
        boolean isAccepted = rs.getBoolean("isAccepted");
        UUID projectId = (UUID) rs.getObject("projectId");

        ProjectRepository projectRepository = new ProjectRepositoryImpl(); // Replace with dependency injection if needed
        Optional<Project> projectOpt = projectRepository.getProjectById(projectId);
        Project project = projectOpt.orElseThrow(() -> new SQLException("Project not found for quote"));

        return new Quote(id, estimatedAmount, issueDate.toLocalDate(), validityDate.toLocalDate(), isAccepted, project);
    }
}
