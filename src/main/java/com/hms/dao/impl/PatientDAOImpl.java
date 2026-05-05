package com.hms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hms.connection.DBConnection;
import com.hms.dao.PatientDAO;
import com.hms.model.Patient;

/**
 * Patient Data Access Object Implementation
 * Handles all database operations for Patient entity using PreparedStatement
 */
public class PatientDAOImpl implements PatientDAO {

	private static final Logger logger = Logger.getLogger(PatientDAOImpl.class.getName());
	private Connection con = DBConnection.connect();

	@Override
	public int addPatient(Patient p) {
		if (p == null || p.getP_id() <= 0 || p.getP_name() == null || p.getP_name().trim().isEmpty()) {
			logger.log(Level.WARNING, "Invalid patient data provided for insertion");
			return 0;
		}

		String sql = "INSERT INTO patient VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, p.getP_id());
			ps.setString(2, p.getP_name().trim());
			ps.setInt(3, p.getP_age());
			ps.setString(4, p.getP_disease() != null ? p.getP_disease().trim() : "");
			ps.setDouble(5, p.getBill());
			int result = ps.executeUpdate();
			if (result > 0) {
				logger.log(Level.INFO, "Patient added successfully: ID=" + p.getP_id());
			}
			return result;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error adding patient: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public List<Patient> getAllPatients() {
		List<Patient> list = new ArrayList<>();
		String sql = "SELECT * FROM patient";

		try (PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(new Patient(
						rs.getInt("p_id"),
						rs.getString("p_name"),
						rs.getInt("p_age"),
						rs.getString("p_disease"),
						rs.getDouble("bill")
						));
			}
			logger.log(Level.INFO, "Retrieved " + list.size() + " patients from database");

		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error retrieving all patients: " + e.getMessage());
		}
		return list;
	}

	@Override
	public Patient getPatientById(int id) {
		if (id <= 0) {
			logger.log(Level.WARNING, "Invalid patient ID provided: " + id);
			return null;
		}

		String sql = "SELECT * FROM patient WHERE p_id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					logger.log(Level.INFO, "Patient found: ID=" + id);
					return new Patient(
							rs.getInt("p_id"),
							rs.getString("p_name"),
							rs.getInt("p_age"),
							rs.getString("p_disease"),
							rs.getDouble("bill")
							);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error retrieving patient by ID " + id + ": " + e.getMessage());
		}
		logger.log(Level.WARNING, "Patient not found: ID=" + id);
		return null;
	}

	@Override
	public int updatePatient(Patient p) {
		if (p == null || p.getP_id() <= 0 || p.getP_name() == null || p.getP_name().trim().isEmpty()) {
			logger.log(Level.WARNING, "Invalid patient data provided for update");
			return 0;
		}

		String sql = "UPDATE patient SET p_name=?, p_age=?, p_disease=?, bill=? WHERE p_id=?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, p.getP_name().trim());
			ps.setInt(2, p.getP_age());
			ps.setString(3, p.getP_disease() != null ? p.getP_disease().trim() : "");
			ps.setDouble(4, p.getBill());
			ps.setInt(5, p.getP_id());
			int result = ps.executeUpdate();
			if (result > 0) {
				logger.log(Level.INFO, "Patient updated successfully: ID=" + p.getP_id());
			} else {
				logger.log(Level.WARNING, "No patient found to update: ID=" + p.getP_id());
			}
			return result;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error updating patient: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public int deletePatient(int id) {
		if (id <= 0) {
			logger.log(Level.WARNING, "Invalid patient ID provided for deletion: " + id);
			return 0;
		}

		String sql = "DELETE FROM patient WHERE p_id=?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			int result = ps.executeUpdate();
			if (result > 0) {
				logger.log(Level.INFO, "Patient deleted successfully: ID=" + id);
			} else {
				logger.log(Level.WARNING, "No patient found to delete: ID=" + id);
			}
			return result;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error deleting patient: " + e.getMessage());
		}
		return 0;
	}
}