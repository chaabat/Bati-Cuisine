-- Enable UUID extension in PostgreSQL
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Enum for Project Status
CREATE TYPE project_status AS ENUM ('IN_PROGRESS', 'COMPLETED', 'CANCELLED');

-- Table for Clients
CREATE TABLE clients (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    isProfessional BOOLEAN NOT NULL
);

-- Table for Projects
CREATE TABLE projects (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    projectName VARCHAR(255) NOT NULL,
    profitMargin DECIMAL(5, 2) NOT NULL,
    totalCost DECIMAL(10, 2),
    status project_status NOT NULL,
    clientId UUID REFERENCES clients(id) ON DELETE CASCADE,
    type VARCHAR(100) NOT NULL,
    surface DECIMAL(10, 2) NOT NULL
);


-- Base Table for Components (Material and Labor)
CREATE TABLE component (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    unitCost DECIMAL(10, 2) NOT NULL,
    quantity DECIMAL(10, 2),
    componentType VARCHAR(50),
    taxRate DECIMAL(4, 2) NOT NULL,
    projectId UUID REFERENCES projects(id) ON DELETE CASCADE
);

-- Table for Materials (inherits from Component)
CREATE TABLE materials (
    componentId UUID PRIMARY KEY REFERENCES component(id) ON DELETE CASCADE,
    qualityCoefficient DECIMAL(3, 2) NOT NULL,
    transportCost DECIMAL(10, 2) NOT NULL
);

-- Table for Labor (inherits from Component)
CREATE TABLE labor (
    componentId UUID PRIMARY KEY REFERENCES component(id) ON DELETE CASCADE,
    hourlyRate DECIMAL(10, 2) NOT NULL,
    hoursWorked DECIMAL(10, 2) NOT NULL,
    productivityFactor DECIMAL(3, 2) NOT NULL
);

-- Table for Quotes (Devis)
CREATE TABLE quotes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    estimatedAmount DECIMAL(10, 2) NOT NULL,
    issueDate DATE NOT NULL,
    validityDate DATE NOT NULL,
    isAccepted BOOLEAN DEFAULT FALSE,
    projectId UUID REFERENCES projects(id) ON DELETE CASCADE
);
