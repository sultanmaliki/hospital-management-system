package com.hms.dao;

import java.util.List;
import com.hms.model.Patient;

public interface PatientDAO {
	int addPatient(Patient p);
	List<Patient> getAllPatients();
	Patient getPatientById(int id);
	int updatePatient(Patient p);
	int deletePatient(int id);
}