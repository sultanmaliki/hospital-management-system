package com.hms.model;

public class Doctor {
	private int d_id;
	private String d_name;
	private String d_speciality;

	public Doctor() {}

	public Doctor(int d_id, String d_name, String d_speciality) {
		this.d_id = d_id;
		this.d_name = d_name;
		this.d_speciality = d_speciality;
	}

	public int getD_id() { return d_id; }
	public void setD_id(int d_id) { this.d_id = d_id; }

	public String getD_name() { return d_name; }
	public void setD_name(String d_name) { this.d_name = d_name; }

	public String getD_speciality() { return d_speciality; }
	public void setD_speciality(String d_speciality) { this.d_speciality = d_speciality; }

	@Override
	public String toString() {
		return d_id + " " + d_name + " " + d_speciality;
	}
}