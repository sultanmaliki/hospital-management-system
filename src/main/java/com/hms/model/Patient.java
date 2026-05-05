package com.hms.model;

public class Patient {
	private int p_id;
	private String p_name;
	private int p_age;
	private String p_disease;
	private double bill;

	public Patient() {}

	public Patient(int p_id, String p_name, int p_age, String p_disease, double bill) {
		this.p_id = p_id;
		this.p_name = p_name;
		this.p_age = p_age;
		this.p_disease = p_disease;
		this.bill = bill;
	}

	public int getP_id() { return p_id; }
	public void setP_id(int p_id) { this.p_id = p_id; }

	public String getP_name() { return p_name; }
	public void setP_name(String p_name) { this.p_name = p_name; }

	public int getP_age() { return p_age; }
	public void setP_age(int p_age) { this.p_age = p_age; }

	public String getP_disease() { return p_disease; }
	public void setP_disease(String p_disease) { this.p_disease = p_disease; }

	public double getBill() { return bill; }
	public void setBill(double bill) { this.bill = bill; }
}