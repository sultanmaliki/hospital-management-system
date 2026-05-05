package com.hms.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hms.config.ConfigLoader;

/**
 * Database Connection Manager
 * Handles MySQL JDBC connections with secure configuration loading.
 * Credentials are loaded from environment variables or application.properties file.
 */
public class DBConnection {

	private static final Logger logger = Logger.getLogger(DBConnection.class.getName());
	private static Connection con;

	/**
	 * Establish and return a database connection.
	 * Configuration is loaded from environment variables or properties file.
	 * @return Database Connection object, or null if connection fails
	 */
	public static Connection connect() {
		try {
			// Load configuration from environment variables or properties file
			String url = ConfigLoader.getDatabaseUrl();
			String username = ConfigLoader.getDatabaseUsername();
			String password = ConfigLoader.getDatabasePassword();
			String driver = ConfigLoader.getJdbcDriver();

			// Load JDBC driver
			Class.forName(driver);
			
			// Establish connection
			con = DriverManager.getConnection(url, username, password);
			logger.log(Level.INFO, "Database connection established successfully.");
			
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "JDBC Driver not found: " + e.getMessage());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to establish database connection: " + e.getMessage());
		}
		
		return con;
	}

	/**
	 * Close the database connection.
	 * @param connection Connection object to close
	 */
	public static void disconnect(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				logger.log(Level.INFO, "Database connection closed successfully.");
			} catch (Exception e) {
				logger.log(Level.WARNING, "Error closing database connection: " + e.getMessage());
			}
		}
	}

	/**
	 * Get the current database connection.
	 * @return Active Connection object
	 */
	public static Connection getConnection() {
		if (con == null) {
			connect();
		}
		return con;
	}
}
