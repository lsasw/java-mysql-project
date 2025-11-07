# Java MySQL Connection Pool Comparison

This project compares the performance of different connection pools for MySQL databases in Java applications.

## Features

- Comparison between HikariCP and Druid connection pools
- Performance testing with multiple threads
- Simple and easy to understand implementation

## Dependencies

- MySQL Connector/J 8.0.33
- HikariCP 5.0.1
- Druid 1.2.20

## How to Run

1. Update database connection details in `DatabaseConfig.java`
2. Run the project with Maven: `mvn exec:java`

## Project Structure

- `ConnectionPoolComparison.java`: Main class for performance comparison
- `DatabaseConfig.java`: Database connection configuration
- `HikariCPDataSource.java`: HikariCP connection pool implementation
- `DruidDataSource.java`: Druid connection pool implementation
- `DatabaseConnection.java`: Basic database connection implementation

## Results

The application will output the time taken by each connection pool to complete the same number of database operations, allowing you to compare their performance.