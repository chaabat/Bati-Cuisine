package com.BatiCuisine.repository.implementation;

import com.BatiCuisine.model.Project;
import com.BatiCuisine.model.ProjectStatus;
import com.BatiCuisine.model.Quote;
import com.BatiCuisine.repository.interfaces.QuoteRepository;
import com.BatiCuisine.config.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class QuoteRepositoryImpl implements QuoteRepository {

    @Override
    public void addQuote(Quote quote) {
        String query = "INSERT INTO quotes (id, estimatedAmount, issueDate, validityDate, isAccepted, projectId) VALUES (uuid_generate_v4(), ?, ?, ?, ?, ?)";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setBigDecimal(1, quote.getEstimatedAmount());  // BigDecimal for amount
            statement.setDate(2, Date.valueOf(quote.getIssueDate()));  // LocalDate to SQL Date
            statement.setDate(3, Date.valueOf(quote.getValidityDate()));  // LocalDate to SQL Date
            statement.setBoolean(4, quote.isAccepted());
            statement.setObject(5, quote.getProject().getId(), java.sql.Types.OTHER);  // UUID for projectId

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error during adding quote: " + e.getMessage());
        }
    }

    @Override
    public Optional<Quote> getQuoteById(UUID id) {
        String query = "SELECT q.*, p.projectName, p.profitMargin, p.status, p.totalCost, p.surface, p.type " +
                "FROM quotes q INNER JOIN projects p ON q.projectId = p.id WHERE q.id = ?";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id, java.sql.Types.OTHER);  // Set UUID as parameter

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Project project = new Project(
                        (UUID) resultSet.getObject("projectId"),
                        resultSet.getString("projectName"),
                        resultSet.getBigDecimal("profitMargin"),
                        ProjectStatus.valueOf(resultSet.getString("status")),
                        resultSet.getBigDecimal("totalCost"),
                        null,  // Assuming client details are fetched separately
                        resultSet.getBigDecimal("surface"),  // Get surface
                        resultSet.getString("type")  // Get project type
                );

                Quote quote = new Quote(
                        (UUID) resultSet.getObject("id"),  // UUID
                        resultSet.getBigDecimal("estimatedAmount"),  // BigDecimal
                        resultSet.getDate("issueDate").toLocalDate(),  // Convert SQL Date to LocalDate
                        resultSet.getDate("validityDate").toLocalDate(),  // Convert SQL Date to LocalDate
                        resultSet.getBoolean("isAccepted"),
                        project
                );
                return Optional.of(quote);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error during fetching quote by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Quote> getAllQuotes() {
        List<Quote> quotes = new ArrayList<>();
        String query = "SELECT q.*, p.projectName, p.profitMargin, p.status, p.totalCost, p.surface, p.type " +
                "FROM quotes q INNER JOIN projects p ON q.projectId = p.id";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Project project = new Project(
                        (UUID) resultSet.getObject("projectId"),
                        resultSet.getString("projectName"),
                        resultSet.getBigDecimal("profitMargin"),
                        ProjectStatus.valueOf(resultSet.getString("status")),
                        resultSet.getBigDecimal("totalCost"),
                        null,  // Assuming client details are fetched separately
                        resultSet.getBigDecimal("surface"),  // Get surface
                        resultSet.getString("type")  // Get project type
                );

                Quote quote = new Quote(
                        (UUID) resultSet.getObject("id"),  // UUID
                        resultSet.getBigDecimal("estimatedAmount"),  // BigDecimal
                        resultSet.getDate("issueDate").toLocalDate(),  // Convert SQL Date to LocalDate
                        resultSet.getDate("validityDate").toLocalDate(),  // Convert SQL Date to LocalDate
                        resultSet.getBoolean("isAccepted"),
                        project
                );
                quotes.add(quote);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error during fetching all quotes: " + e.getMessage());
        }
        return quotes;
    }
}
