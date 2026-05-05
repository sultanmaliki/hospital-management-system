package com.hms.dao;

import java.util.List;
import com.hms.model.Appointment;

public interface AppointmentDAO {
	int bookAppointment(Appointment a);
	List<Appointment> getAllAppointments();
	List<Appointment> getAppointmentsByPatient(int p_id);
	List<Appointment> getAppointmentsByDoctor(int d_id);
	int updateAppointment(Appointment a);
	int cancelAppointment(int appointment_id);
}