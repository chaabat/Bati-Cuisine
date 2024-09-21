# Bati-Cuisine

## Description

**Bati-Cuisine** is a Java-based project management application designed to assist construction professionals and individuals in planning and tracking their renovation and construction projects. This application provides an intuitive interface for managing clients, projects, materials, and labor.

## Features

### Client Management
- **Add a Client**: Register contact information for clients.

[//]: # (- **Update a Client**: Modify details of existing clients.)

[//]: # (- **Delete a Client**: Remove clients from the database.)

### Project Management
- **Create a Project**: Initiate a new project with details such as name, type (Renovation or Construction), surface area, budget, and dates.
- **Track Progress**: Update the status of ongoing projects.
- **Project History**: Review past projects for future reference.

### Material Management
- **Add Materials**: Register necessary materials with their prices and suppliers.

[//]: # (- **Update Materials**: Modify material details such as quantity, cost, and quality.)

[//]: # (- **Remove Materials**: Delete materials no longer needed for a project.)

### Labor Management
- **Register Workers**: Add information about workers, their skills, and hourly rates.

[//]: # (- **Update Labor Records**: Track the hours worked and productivity for each worker.)

[//]: # (- **Remove Workers**: Remove workers from a project if necessary.)

### Cost Calculation
- **Estimate Costs**: Calculate the total cost of a project, including materials, labor, and any additional expenses.
- **Apply Taxes and Margins**: Add VAT and profit margins to the estimated costs.

### Quote Generation
- **Create a Quote**: Generate quotes based on ongoing projects, including all estimated costs and details.
- **Manage Quotes**: Track the status of quotes, including issue and validity dates, and whether the client has accepted the quote.

## Technologies Used

- **Java 8**: The programming language used for developing the application.
- **PostgreSQL**: The relational database management system for storing application data.
- **JDBC**: Java Database Connectivity for interacting with the PostgreSQL database.


## Installation

### Prerequisites

- **Java Development Kit (JDK) 8 or higher**: Make sure you have JDK 8 or a newer version installed on your machine.
- **PostgreSQL**: Install PostgreSQL and set up a database for the application.
- **JDBC Driver**: Download the PostgreSQL JDBC driver (e.g., `postgresql-<version>.jar`) and place it in your project's `libs` directory.
- **Maven**: Ensure Maven is installed for building and managing project dependencies.

### Installation Steps

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/chaabat/Bati-Cuisine.git
   cd Bati-Cuisine
