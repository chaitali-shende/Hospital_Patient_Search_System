package com.hs.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hs.entity.Patient;
import com.hs.exception.ResourceNotFoundException;
import com.hs.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements Patientservice {
	private final PatientRepository patientRepository;

	@Override
	public Patient getPatientById(Long id) {
		return patientRepository.findById(id).orElseThrow();
	}

	@Override
	public List<Patient> getPatientsByDisease(String disease) {
		List<Patient> list = patientRepository.findByDisease(disease);
		if (list.isEmpty())
			throw new ResourceNotFoundException("No patients with disease: " + disease);
		return list;
	}

	@Override
	public List<Patient> getPatientsAdmittedAfter(LocalDate date) {
		List<Patient> list = patientRepository.findByAdmissionDateAfter(date);
		if (list.isEmpty())
			throw new ResourceNotFoundException("No patients admitted after: " + date);
		return list;
	}

	@Override
	public List<Patient> getActivePatients() {
		List<Patient> list = patientRepository.findByDischargedFalse();
		if (list.isEmpty())
			throw new ResourceNotFoundException("No active patients found");
		return list;
	}

}
