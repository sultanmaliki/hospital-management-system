package com.hms.model;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
	private int appointment_id;
	private int p_id;
	private int d_id;
	private Date date;
	private Time time;

	public Appointment() {}

	public Appointment(int appointment_id, int p_id, int d_id, Date date, Time time) {
		this.appointment_id = appointment_id;
		this.p_id = p_id;
		this.d_id = d_id;
		this.date = date;
		this.time = time;
	}

	public int getAppointment_id() { return appointment_id; }
	public void setAppointment_id(int appointment_id) { this.appointment_id = appointment_id; }

	public int getP_id() { return p_id; }
	public void setP_id(int p_id) { this.p_id = p_id; }

	public int getD_id() { return d_id; }
	public void setD_id(int d_id) { this.d_id = d_id; }

	public Date getDate() { return date; }
	public void setDate(Date date) { this.date = date; }

	public Time getTime() { return time; }
	public void setTime(Time time) { this.time = time; }

	@Override
	public String toString() {
		return appointment_id + " | Patient: " + p_id + " | Doctor: " + d_id +
				" | " + date + " " + time;
	}
}