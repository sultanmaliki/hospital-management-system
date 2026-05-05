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
import com.hms.dao.AppointmentDAO;
import com.hms.model.Appointment;

/**
 * Appointment Data Access Object Implementation
 * Handles all database operations for Appointment entity using PreparedStatement
 */
public class AppointmentDAOImpl implements AppointmentDAO {

	private static final Logger logger = Logger.getLogger(AppointmentDAOImpl.class.getName());
	private Connection con = DBConnection.connect();

	@Override
	public int bookAppointment(Appointment a) {
		if (a == null || a.getAppointment_id() <= 0 || a.getP_id() <= 0 || a.getD_id() <= 0 
				|| a.getDate() == null || a.getTime() == null) {
			logger.log(Level.WARNING, "Invalid appointment data provided for booking");
			return 0;
		}

		String sql = "INSERT INTO appointment VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, a.getAppointment_id());
			ps.setInt(2, a.getP_id());
			ps.setInt(3, a.getD_id());
			ps.setDate(4, a.getDate());
			ps.setTime(5, a.getTime());

			int result = ps.executeUpdate();
			if (result > 0) {
				logger.log(Level.INFO, "Appointment booked successfully: ID=" + a.getAppointment_id());
			}
			return result;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error booking appointment: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public List<Appointment> getAllAppointments() {
		List<Appointment> list = new ArrayList<>();
		String sql = "SELECT * FROM appointment";

		try (PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(new Appointment(
						rs.getInt("appointment_id"),
						rs.getInt("p_id"),
						rs.getInt("d_id"),
						rs.getDate("date"),
						rs.getTime("time")
						));
			}
			logger.log(Level.INFO, "Retrieved " + list.size() + " appointments from database");

		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error retrieving all appointments: " + e.getMessage());
		}
		return list;
	}

	@Override
	public List<Appointment> getAppointmentsByPatient(int p_id) {
		if (p_id <= 0) {
			logger.log(Level.WARNING, "Invalid patient ID provided: " + p_id);
			return new ArrayList<>();
		}

		List<Appointment> list = new ArrayList<>();
		String sql = "SELECT * FROM appointment WHERE p_id=?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, p_id);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(new Appointment(
							rs.getInt("appointment_id"),
							rs.getInt("p_id"),
							rs.getInt("d_id"),
							rs.getDate("date"),
							rs.getTime("time")
							));
				}
				logger.log(Level.INFO, "Retrieved " + list.size() + " appointments for patient: ID=" + p_id);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error retrieving appointments for patient " + p_id + ": " + e.getMessage());
		}
		return list;
	}

	@Override
	public List<Appointment> getAppointmentsByDoctor(int d_id) {
		if (d_id <= 0) {
			logger.log(Level.WARNING, "Invalid doctor ID provided: " + d_id);
			return new ArrayList<>();
		}

		List<Appointment> list = new ArrayList<>();
		String sql = "SELECT * FROM appointment WHERE d_id=?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, d_id);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(new Appointment(
							rs.getInt("appointment_id"),
							rs.getInt("p_id"),
							rs.getInt("d_id"),
							rs.getDate("date"),
							rs.getTime("time")
							));
				}
				logger.log(Level.INFO, "Retrieved " + list.size() + " appointments for doctor: ID=" + d_id);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error retrieving appointments for doctor " + d_id + ": " + e.getMessage());
		}
		return list;
	}

	@Override
	public int updateAppointment(Appointment a) {
		if (a == null || a.getAppointment_id() <= 0 || a.getP_id() <= 0 || a.getD_id() <= 0
				|| a.getDate() == null || a.getTime() == null) {
			logger.log(Level.WARNING, "Invalid appointment data provided for update");
			return 0;
		}

		String sql = "UPDATE appointment SET p_id=?, d_id=?, date=?, time=? WHERE appointment_id=?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, a.getP_id());
			ps.setInt(2, a.getD_id());
			ps.setDate(3, a.getDate());
			ps.setTime(4, a.getTime());
			ps.setInt(5, a.getAppointment_id());

			int result = ps.executeUpdate();
			if (result > 0) {
				logger.log(Level.INFO, "Appointment updated successfully: ID=" + a.getAppointment_id());
			} else {
				logger.log(Level.WARNING, "No appointment found to update: ID=" + a.getAppointment_id());
			}
			return result;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error updating appointment: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public int cancelAppointment(int appointment_id) {
		if (appointment_id <= 0) {
			logger.log(Level.WARNING, "Invalid appointment ID provided for cancellation: " + appointment_id);
			return 0;
		}

		String sql = "DELETE FROM appointment WHERE appointment_id=?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, appointment_id);
			int result = ps.executeUpdate();
			if (result > 0) {
				logger.log(Level.INFO, "Appointment cancelled successfully: ID=" + appointment_id);
			} else {
				logger.log(Level.WARNING, "No appointment found to cancel: ID=" + appointment_id);
			}
			return result;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error cancelling appointment: " + e.getMessage());
		}
		return 0;
	}
}