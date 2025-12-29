package com.hs.service;

import java.time.LocalDate;
import java.util.List;

import com.hs.entity.Patient;

public interface Patientservice{
	 public Patient getPatientById(Long id) ;
	 public List<Patient> getPatientsByDisease(String disease);
	 public List<Patient> getPatientsAdmittedAfter(LocalDate date);
	 public List<Patient> getActivePatients();
}
