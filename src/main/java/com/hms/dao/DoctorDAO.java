package com.hms.dao;

import java.util.List;
import com.hms.model.Doctor;

public interface DoctorDAO {
	int addDoctor(Doctor d);
	List<Doctor> getAllDoctors();
	Doctor getDoctorById(int id);
	int updateDoctor(Doctor d);
	int deleteDoctor(int id);
}