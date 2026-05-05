package com.hms.main;

import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hms.dao.impl.AppointmentDAOImpl;
import com.hms.dao.impl.DoctorDAOImpl;
import com.hms.dao.impl.PatientDAOImpl;
import com.hms.model.Appointment;
import com.hms.model.Doctor;
import com.hms.model.Patient;

/**
 * Hospital Management System - Main Application
 * Console-based menu-driven interface for managing patients, doctors, and appointments
 */
public class MainApp {

	private static final Logger logger = Logger.getLogger(MainApp.class.getName());
	private static Scanner sc = new Scanner(System.in);

	private static PatientDAOImpl patientDAO = new PatientDAOImpl();
	private static DoctorDAOImpl doctorDAO = new DoctorDAOImpl();
	private static AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();

	public static void main(String[] args) {
		logger.log(Level.INFO, "Hospital Management System started");

		while (true) {
			try {
				System.out.println("\n===== HOSPITAL MANAGEMENT SYSTEM =====");
				System.out.println("1. Patient Module");
				System.out.println("2. Doctor Module");
				System.out.println("3. Appointment Module");
				System.out.println("4. Exit");
				System.out.print("Enter choice: ");

				int choice = getValidChoice();

				switch (choice) {
				case 1:
					patientMenu();
					break;
				case 2:
					doctorMenu();
					break;
				case 3:
					appointmentMenu();
					break;
				case 4:
					System.out.println("Exiting Hospital Management System. Goodbye!");
					logger.log(Level.INFO, "Application exited normally");
					return;
				default:
					System.out.println("Invalid choice. Please select 1-4.");
				}
			} catch (Exception e) {
				logger.log(Level.WARNING, "Error in main menu: " + e.getMessage());
				System.out.println("An error occurred. Please try again.");
				sc.nextLine(); // Clear buffer
			}
		}
	}

	/**
	 * Patient Module Menu - Add, View, Delete Patients
	 */
	private static void patientMenu() {
		System.out.println("\n--- PATIENT MODULE ---");
		System.out.println("1. Add Patient");
		System.out.println("2. View All Patients");
		System.out.println("3. Update Patient");
		System.out.println("4. Delete Patient");
		System.out.println("5. Back to Main Menu");
		System.out.print("Enter choice: ");

		int ch = getValidChoice();
		sc.nextLine(); // consume newline

		switch (ch) {
		case 1:
			addPatient();
			break;
		case 2:
			viewAllPatients();
			break;
		case 3:
			updatePatientRecord();
			break;
		case 4:
			deletePatientRecord();
			break;
		case 5:
			System.out.println("Returning to Main Menu...");
			break;
		default:
			System.out.println("Invalid choice");
		}
	}

	/**
	 * Add a new patient
	 */
	private static void addPatient() {
		System.out.print("Patient ID: ");
		int id = getValidPositiveInt();
		if (id <= 0) {
			System.out.println("Invalid ID. ID must be greater than 0.");
			return;
		}

		System.out.print("Name: ");
		String name = sc.nextLine().trim();
		if (name.isEmpty()) {
			System.out.println("Name cannot be empty.");
			return;
		}

		System.out.print("Age: ");
		int age = getValidPositiveInt();
		if (age <= 0 || age > 150) {
			System.out.println("Invalid age. Please enter a valid age (1-150).");
			return;
		}

		System.out.print("Disease: ");
		String disease = sc.nextLine().trim();

		System.out.print("Bill Amount: ");
		double bill = getValidDouble();
		if (bill < 0) {
			System.out.println("Bill amount cannot be negative.");
			return;
		}

		Patient p = new Patient(id, name, age, disease, bill);
		int result = patientDAO.addPatient(p);
		System.out.println(result == 1 ? "✓ Patient added successfully" : "✗ Failed to add patient");
	}

	/**
	 * View all patients
	 */
	private static void viewAllPatients() {
		patientDAO.getAllPatients().forEach(System.out::println);
	}

	/**
	 * Update patient information
	 */
	private static void updatePatientRecord() {
		System.out.print("Enter Patient ID to update: ");
		int id = getValidPositiveInt();
		if (id <= 0) {
			System.out.println("Invalid ID.");
			return;
		}

		Patient existing = patientDAO.getPatientById(id);
		if (existing == null) {
			System.out.println("Patient not found.");
			return;
		}

		System.out.print("New Name (current: " + existing.getP_name() + "): ");
		String name = sc.nextLine().trim();
		if (name.isEmpty()) {
			name = existing.getP_name();
		}

		System.out.print("New Age (current: " + existing.getP_age() + "): ");
		String ageStr = sc.nextLine().trim();
		int age = existing.getP_age();
		if (!ageStr.isEmpty()) {
			try {
				age = Integer.parseInt(ageStr);
				if (age <= 0 || age > 150) {
					System.out.println("Invalid age. Keeping current.");
					age = existing.getP_age();
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Keeping current age.");
			}
		}

		System.out.print("New Disease (current: " + existing.getP_disease() + "): ");
		String disease = sc.nextLine().trim();
		if (disease.isEmpty()) {
			disease = existing.getP_disease();
		}

		System.out.print("New Bill (current: " + existing.getBill() + "): ");
		String billStr = sc.nextLine().trim();
		double bill = existing.getBill();
		if (!billStr.isEmpty()) {
			try {
				bill = Double.parseDouble(billStr);
				if (bill < 0) {
					System.out.println("Invalid bill. Keeping current.");
					bill = existing.getBill();
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Keeping current bill.");
			}
		}

		Patient updated = new Patient(id, name, age, disease, bill);
		int result = patientDAO.updatePatient(updated);
		System.out.println(result == 1 ? "✓ Patient updated successfully" : "✗ Failed to update patient");
	}

	/**
	 * Delete a patient
	 */
	private static void deletePatientRecord() {
		System.out.print("Enter Patient ID to delete: ");
		int id = getValidPositiveInt();
		if (id <= 0) {
			System.out.println("Invalid ID.");
			return;
		}

		int result = patientDAO.deletePatient(id);
		System.out.println(result == 1 ? "✓ Patient deleted successfully" : "✗ Failed to delete patient");
	}

	/**
	 * Doctor Module Menu - Add, View, Delete Doctors
	 */
	private static void doctorMenu() {
		System.out.println("\n--- DOCTOR MODULE ---");
		System.out.println("1. Add Doctor");
		System.out.println("2. View All Doctors");
		System.out.println("3. Update Doctor");
		System.out.println("4. Delete Doctor");
		System.out.println("5. Back to Main Menu");
		System.out.print("Enter choice: ");

		int ch = getValidChoice();
		sc.nextLine();

		switch (ch) {
		case 1:
			addDoctor();
			break;
		case 2:
			viewAllDoctors();
			break;
		case 3:
			updateDoctorRecord();
			break;
		case 4:
			deleteDoctorRecord();
			break;
		case 5:
			System.out.println("Returning to Main Menu...");
			break;
		default:
			System.out.println("Invalid choice");
		}
	}

	/**
	 * Add a new doctor
	 */
	private static void addDoctor() {
		System.out.print("Doctor ID: ");
		int id = getValidPositiveInt();
		if (id <= 0) {
			System.out.println("Invalid ID. ID must be greater than 0.");
			return;
		}

		System.out.print("Name: ");
		String name = sc.nextLine().trim();
		if (name.isEmpty()) {
			System.out.println("Name cannot be empty.");
			return;
		}

		System.out.print("Speciality: ");
		String spec = sc.nextLine().trim();
		if (spec.isEmpty()) {
			System.out.println("Speciality cannot be empty.");
			return;
		}

		Doctor d = new Doctor(id, name, spec);
		int result = doctorDAO.addDoctor(d);
		System.out.println(result == 1 ? "✓ Doctor added successfully" : "✗ Failed to add doctor");
	}

	/**
	 * View all doctors
	 */
	private static void viewAllDoctors() {
		doctorDAO.getAllDoctors().forEach(System.out::println);
	}

	/**
	 * Update doctor information
	 */
	private static void updateDoctorRecord() {
		System.out.print("Enter Doctor ID to update: ");
		int id = getValidPositiveInt();
		if (id <= 0) {
			System.out.println("Invalid ID.");
			return;
		}

		Doctor existing = doctorDAO.getDoctorById(id);
		if (existing == null) {
			System.out.println("Doctor not found.");
			return;
		}

		System.out.print("New Name (current: " + existing.getD_name() + "): ");
		String name = sc.nextLine().trim();
		if (name.isEmpty()) {
			name = existing.getD_name();
		}

		System.out.print("New Speciality (current: " + existing.getD_speciality() + "): ");
		String spec = sc.nextLine().trim();
		if (spec.isEmpty()) {
			spec = existing.getD_speciality();
		}

		Doctor updated = new Doctor(id, name, spec);
		int result = doctorDAO.updateDoctor(updated);
		System.out.println(result == 1 ? "✓ Doctor updated successfully" : "✗ Failed to update doctor");
	}

	/**
	 * Delete a doctor
	 */
	private static void deleteDoctorRecord() {
		System.out.print("Enter Doctor ID to delete: ");
		int id = getValidPositiveInt();
		if (id <= 0) {
			System.out.println("Invalid ID.");
			return;
		}

		int result = doctorDAO.deleteDoctor(id);
		System.out.println(result == 1 ? "✓ Doctor deleted successfully" : "✗ Failed to delete doctor");
	}

	/**
	 * Appointment Module Menu - Book, View, Cancel Appointments
	 */
	private static void appointmentMenu() {
		System.out.println("\n--- APPOINTMENT MODULE ---");
		System.out.println("1. Book Appointment");
		System.out.println("2. View All Appointments");
		System.out.println("3. View Patient Appointments");
		System.out.println("4. View Doctor Appointments");
		System.out.println("5. Update Appointment");
		System.out.println("6. Cancel Appointment");
		System.out.println("7. Back to Main Menu");
		System.out.print("Enter choice: ");

		int ch = getValidChoice();
		sc.nextLine();

		switch (ch) {
		case 1:
			bookAppointment();
			break;
		case 2:
			viewAllAppointments();
			break;
		case 3:
			viewPatientAppointments();
			break;
		case 4:
			viewDoctorAppointments();
			break;
		case 5:
			updateAppointmentRecord();
			break;
		case 6:
			cancelAppointmentRecord();
			break;
		case 7:
			System.out.println("Returning to Main Menu...");
			break;
		default:
			System.out.println("Invalid choice");
		}
	}

	/**
	 * Book an appointment
	 */
	private static void bookAppointment() {
		System.out.print("Appointment ID: ");
		int aid = getValidPositiveInt();
		if (aid <= 0) {
			System.out.println("Invalid appointment ID.");
			return;
		}

		System.out.print("Patient ID: ");
		int pid = getValidPositiveInt();
		if (pid <= 0) {
			System.out.println("Invalid patient ID.");
			return;
		}

		System.out.print("Doctor ID: ");
		int did = getValidPositiveInt();
		if (did <= 0) {
			System.out.println("Invalid doctor ID.");
			return;
		}

		System.out.print("Date (yyyy-mm-dd): ");
		String dateStr = sc.nextLine().trim();
		Date appointmentDate;
		try {
			appointmentDate = Date.valueOf(dateStr);
		} catch (Exception e) {
			System.out.println("Invalid date format. Please use yyyy-mm-dd");
			return;
		}

		System.out.print("Time (hh:mm:ss): ");
		String timeStr = sc.nextLine().trim();
		Time appointmentTime;
		try {
			appointmentTime = Time.valueOf(timeStr);
		} catch (Exception e) {
			System.out.println("Invalid time format. Please use hh:mm:ss");
			return;
		}

		Appointment a = new Appointment(aid, pid, did, appointmentDate, appointmentTime);
		int result = appointmentDAO.bookAppointment(a);
		System.out.println(result == 1 ? "✓ Appointment booked successfully" : "✗ Failed to book appointment");
	}

	/**
	 * View all appointments
	 */
	private static void viewAllAppointments() {
		appointmentDAO.getAllAppointments().forEach(System.out::println);
	}

	/**
	 * View appointments by patient
	 */
	private static void viewPatientAppointments() {
		System.out.print("Enter Patient ID: ");
		int pid = getValidPositiveInt();
		if (pid <= 0) {
			System.out.println("Invalid patient ID.");
			return;
		}

		appointmentDAO.getAppointmentsByPatient(pid).forEach(System.out::println);
	}

	/**
	 * View appointments by doctor
	 */
	private static void viewDoctorAppointments() {
		System.out.print("Enter Doctor ID: ");
		int did = getValidPositiveInt();
		if (did <= 0) {
			System.out.println("Invalid doctor ID.");
			return;
		}

		appointmentDAO.getAppointmentsByDoctor(did).forEach(System.out::println);
	}

	/**
	 * Update an appointment
	 */
	private static void updateAppointmentRecord() {
		System.out.print("Enter Appointment ID to update: ");
		int aid = getValidPositiveInt();
		if (aid <= 0) {
			System.out.println("Invalid appointment ID.");
			return;
		}

		System.out.print("New Patient ID: ");
		int pid = getValidPositiveInt();
		if (pid <= 0) {
			System.out.println("Invalid patient ID.");
			return;
		}

		System.out.print("New Doctor ID: ");
		int did = getValidPositiveInt();
		if (did <= 0) {
			System.out.println("Invalid doctor ID.");
			return;
		}

		System.out.print("New Date (yyyy-mm-dd): ");
		String dateStr = sc.nextLine().trim();
		Date appointmentDate;
		try {
			appointmentDate = Date.valueOf(dateStr);
		} catch (Exception e) {
			System.out.println("Invalid date format. Please use yyyy-mm-dd");
			return;
		}

		System.out.print("New Time (hh:mm:ss): ");
		String timeStr = sc.nextLine().trim();
		Time appointmentTime;
		try {
			appointmentTime = Time.valueOf(timeStr);
		} catch (Exception e) {
			System.out.println("Invalid time format. Please use hh:mm:ss");
			return;
		}

		Appointment a = new Appointment(aid, pid, did, appointmentDate, appointmentTime);
		int result = appointmentDAO.updateAppointment(a);
		System.out.println(result == 1 ? "✓ Appointment updated successfully" : "✗ Failed to update appointment");
	}

	/**
	 * Cancel an appointment
	 */
	private static void cancelAppointmentRecord() {
		System.out.print("Enter Appointment ID to cancel: ");
		int id = getValidPositiveInt();
		if (id <= 0) {
			System.out.println("Invalid appointment ID.");
			return;
		}

		int result = appointmentDAO.cancelAppointment(id);
		System.out.println(result == 1 ? "✓ Appointment cancelled successfully" : "✗ Failed to cancel appointment");
	}

	// ================= INPUT VALIDATION HELPERS =================

	/**
	 * Get valid positive integer from user input
	 */
	private static int getValidPositiveInt() {
		try {
			return sc.nextInt();
		} catch (Exception e) {
			sc.nextLine(); // Clear buffer
			return -1;
		}
	}

	/**
	 * Get valid choice (1-7)
	 */
	private static int getValidChoice() {
		try {
			return sc.nextInt();
		} catch (Exception e) {
			sc.nextLine(); // Clear buffer
			return -1;
		}
	}

	/**
	 * Get valid double from user input
	 */
	private static double getValidDouble() {
		try {
			return sc.nextDouble();
		} catch (Exception e) {
			sc.nextLine(); // Clear buffer
			return -1.0;
		}
	}
}