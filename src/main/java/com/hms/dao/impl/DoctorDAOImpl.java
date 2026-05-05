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
import com.hms.dao.DoctorDAO;
import com.hms.model.Doctor;

/**
 * Doctor Data Access Object Implementation
 * Handles all database operations for Doctor entity using PreparedStatement
 */
public class DoctorDAOImpl implements DoctorDAO {

	private static final Logger logger = Logger.getLogger(DoctorDAOImpl.class.getName());
	private Connection con = DBConnection.connect();

	@Override
	public int addDoctor(Doctor d) {
		if (d == null || d.getD_id() <= 0 || d.getD_name() == null || d.getD_name().trim().isEmpty()) {
			logger.log(Level.WARNING, "Invalid doctor data provided for insertion");
			return 0;
		}

		String sql = "INSERT INTO doctor VALUES (?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, d.getD_id());
			ps.setString(2, d.getD_name().trim());
			ps.setString(3, d.getD_speciality() != null ? d.getD_speciality().trim() : "");
			int result = ps.executeUpdate();
			if (result > 0) {
				logger.log(Level.INFO, "Doctor added successfully: ID=" + d.getD_id());
			}
			return result;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error adding doctor: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public List<Doctor> getAllDoctors() {
		List<Doctor> list = new ArrayList<>();
		String sql = "SELECT * FROM doctor";

		try (PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(new Doctor(
						rs.getInt("d_id"),
						rs.getString("d_name"),
						rs.getString("d_speciality")
						));
			}
			logger.log(Level.INFO, "Retrieved " + list.size() + " doctors from database");

		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error retrieving all doctors: " + e.getMessage());
		}
		return list;
	}

	@Override
	public Doctor getDoctorById(int id) {
		if (id <= 0) {
			logger.log(Level.WARNING, "Invalid doctor ID provided: " + id);
			return null;
		}

		String sql = "SELECT * FROM doctor WHERE d_id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					logger.log(Level.INFO, "Doctor found: ID=" + id);
					return new Doctor(
							rs.getInt("d_id"),
							rs.getString("d_name"),
							rs.getString("d_speciality")
							);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error retrieving doctor by ID " + id + ": " + e.getMessage());
		}
		logger.log(Level.WARNING, "Doctor not found: ID=" + id);
		return null;
	}

	@Override
	public int updateDoctor(Doctor d) {
		if (d == null || d.getD_id() <= 0 || d.getD_name() == null || d.getD_name().trim().isEmpty()) {
			logger.log(Level.WARNING, "Invalid doctor data provided for update");
			return 0;
		}

		String sql = "UPDATE doctor SET d_name=?, d_speciality=? WHERE d_id=?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, d.getD_name().trim());
			ps.setString(2, d.getD_speciality() != null ? d.getD_speciality().trim() : "");
			ps.setInt(3, d.getD_id());
			int result = ps.executeUpdate();
			if (result > 0) {
				logger.log(Level.INFO, "Doctor updated successfully: ID=" + d.getD_id());
			} else {
				logger.log(Level.WARNING, "No doctor found to update: ID=" + d.getD_id());
			}
			return result;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error updating doctor: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public int deleteDoctor(int id) {
		if (id <= 0) {
			logger.log(Level.WARNING, "Invalid doctor ID provided for deletion: " + id);
			return 0;
		}

		String sql = "DELETE FROM doctor WHERE d_id=?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			int result = ps.executeUpdate();
			if (result > 0) {
				logger.log(Level.INFO, "Doctor deleted successfully: ID=" + id);
			} else {
				logger.log(Level.WARNING, "No doctor found to delete: ID=" + id);
			}
			return result;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error deleting doctor: " + e.getMessage());
		}
		return 0;
	}
}