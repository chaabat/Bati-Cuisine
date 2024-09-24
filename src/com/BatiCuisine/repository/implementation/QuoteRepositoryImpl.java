package com.BatiCuisine.repository.implementation;

import com.BatiCuisine.model.Quote;
import com.BatiCuisine.repository.interfaces.QuoteRepository;
import com.BatiCuisine.config.DataBaseConnection;

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
            statement.setDate(3, java.sql.Date.valueOf(quote.getIssueDate()));
            statement.setDate(4, java.sql.Date.valueOf(quote.getValidityDate()));
            statement.setBoolean(5, quote.isAccepted());
            statement.setObject(6, quote.getProject().getId(), Types.OTHER);

            statement.executeUpdate();
            quoteCache.put(quoteId, quote);

        } catch (SQLException e) {
            throw new RuntimeException("Error adding quote to the database: " + e.getMessage(), e);
        }
    }



}
