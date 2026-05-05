package com.hms.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuration loader for database connection properties.
 * Loads configuration from environment variables or properties file.
 * Priority: Environment Variables > application.properties > Default values
 */
public class ConfigLoader {
	
	private static final Logger logger = Logger.getLogger(ConfigLoader.class.getName());
	private static final Properties properties = new Properties();
	
	// Default configuration
	private static final String DEFAULT_DB_URL = "jdbc:mysql://localhost:3306/hospital";
	private static final String DEFAULT_DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	
	static {
		loadConfiguration();
	}
	
	/**
	 * Load configuration from properties file if it exists.
	 * Properties can be overridden by environment variables.
	 */
	private static void loadConfiguration() {
		try (InputStream input = ConfigLoader.class.getClassLoader()
				.getResourceAsStream("application.properties")) {
			if (input != null) {
				properties.load(input);
				logger.log(Level.INFO, "Configuration loaded from application.properties");
			} else {
				logger.log(Level.WARNING, "application.properties not found. Using environment variables or defaults.");
			}
		} catch (IOException e) {
			logger.log(Level.WARNING, "Error loading configuration file: " + e.getMessage());
		}
	}
	
	/**
	 * Get database URL from environment or properties file.
	 * @return Database connection URL
	 */
	public static String getDatabaseUrl() {
		String envUrl = System.getenv("DB_URL");
		if (envUrl != null && !envUrl.isEmpty()) {
			return envUrl;
		}
		String propUrl = properties.getProperty("DB_URL");
		if (propUrl != null && !propUrl.isEmpty()) {
			return propUrl;
		}
		logger.log(Level.WARNING, "DB_URL not configured. Using default.");
		return DEFAULT_DB_URL;
	}
	
	/**
	 * Get database username from environment or properties file.
	 * @return Database username
	 */
	public static String getDatabaseUsername() {
		String envUsername = System.getenv("DB_USERNAME");
		if (envUsername != null && !envUsername.isEmpty()) {
			return envUsername;
		}
		String propUsername = properties.getProperty("DB_USERNAME");
		if (propUsername != null && !propUsername.isEmpty()) {
			return propUsername;
		}
		logger.log(Level.WARNING, "DB_USERNAME not configured.");
		return "";
	}
	
	/**
	 * Get database password from environment or properties file.
	 * @return Database password
	 */
	public static String getDatabasePassword() {
		String envPassword = System.getenv("DB_PASSWORD");
		if (envPassword != null && !envPassword.isEmpty()) {
			return envPassword;
		}
		String propPassword = properties.getProperty("DB_PASSWORD");
		if (propPassword != null && !propPassword.isEmpty()) {
			return propPassword;
		}
		logger.log(Level.WARNING, "DB_PASSWORD not configured.");
		return "";
	}
	
	/**
	 * Get JDBC driver class name.
	 * @return JDBC driver class
	 */
	public static String getJdbcDriver() {
		String envDriver = System.getenv("DB_DRIVER");
		if (envDriver != null && !envDriver.isEmpty()) {
			return envDriver;
		}
		String propDriver = properties.getProperty("DB_DRIVER");
		if (propDriver != null && !propDriver.isEmpty()) {
			return propDriver;
		}
		return DEFAULT_DB_DRIVER;
	}
}
