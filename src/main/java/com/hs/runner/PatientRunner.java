package com.hs.runner;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hs.entity.Doctor;
import com.hs.entity.Hospital;
import com.hs.entity.Patient;
import com.hs.exception.ResourceNotFoundException;
import com.hs.repository.DoctorRepository;
import com.hs.repository.HospitalRepository;
import com.hs.repository.PatientRepository;
import com.hs.service.Patientservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PatientRunner implements CommandLineRunner {

	private final HospitalRepository hospitalRepo;
	private final DoctorRepository doctorRepo;
	private final PatientRepository patientRepo;
	private final Patientservice patientService;

	@Override
	public void run(String... args) {

		printHeader();

		insertSampleData();

		executeSearchOperations();

		printFooter();
	}

	// ---------------------- DATA INSERTION ----------------------

	private void insertSampleData() {

		String hospitalName = "City Care Hospital";

		if (hospitalRepo.existsByHospitalName(hospitalName)) {
			log.warn("Hospital '{}' already exists. Skipping data insertion.", hospitalName);
			return;
		}

		log.info("Inserting hospital, doctors, and patients data...");

		Hospital hospital = Hospital.builder().hospitalName(hospitalName).location("Bangalore").build();

		Doctor d1 = Doctor.builder().doctorName("Dr. Smith").specialization("Diabetologist").experience(15)
				.hospital(hospital).build();

		Doctor d2 = Doctor.builder().doctorName("Dr. Khan").specialization("General Physician").experience(10)
				.hospital(hospital).build();

		Doctor d3 = Doctor.builder().doctorName("Dr. Turner").specialization("Cardiologist").experience(8)
				.hospital(hospital).build();

		Patient p1 = buildPatient("John", "Diabetes", 45, LocalDate.of(2022, 6, 15), true, hospital, d1);

		Patient p2 = buildPatient("Rahman", "Fever", 30, LocalDate.of(2025, 1, 10), false, hospital, d2);

		Patient p3 = buildPatient("Bob", "Heart Surgery", 30, LocalDate.of(2025, 1, 11), false, hospital, d3);

		hospital.setDoctors(List.of(d1, d2, d3));
		hospital.setPatients(List.of(p1, p2, p3));

		d1.setPatients(List.of(p1));
		d2.setPatients(List.of(p2));
		d3.setPatients(List.of(p3));

		hospitalRepo.save(hospital);

		log.info("Sample data inserted successfully ✔");
	}

	private Patient buildPatient(String name, String disease, int age, LocalDate date, boolean discharged,
			Hospital hospital, Doctor doctor) {

		return Patient.builder().patientName(name).disease(disease).age(age).admissionDate(date).discharged(discharged)
				.hospital(hospital).doctor(doctor).build();
	}

	// ---------------------- SEARCH OPERATIONS ----------------------

	private void executeSearchOperations() {

		try {
			logSection("Patients with Disease = Diabetes");
			patientService.getPatientsByDisease("Diabetes").forEach(this::logPatient);

			logSection("Patients admitted after 2024");
			patientService.getPatientsAdmittedAfter(LocalDate.of(2024, 1, 1)).forEach(this::logPatient);

			logSection("Patients not discharged");
			patientService.getActivePatients().forEach(this::logPatient);

		} catch (ResourceNotFoundException ex) {
			log.error("Search failed ❌ : {}", ex.getMessage());
		}
	}

	// ---------------------- LOG HELPERS ----------------------

	private void logPatient(Patient p) {
		log.info("PatientId: {}, Name: {}, Disease: {}, Doctor: {}, Hospital: {}", p.getPatientId(), p.getPatientName(),
				p.getDisease(), p.getDoctor().getDoctorName(), p.getHospital().getHospitalName());
	}

	private void logSection(String title) {
		log.info("--------------------------------------------------");
		log.info(" {}", title);
		log.info("--------------------------------------------------");
	}

	private void printHeader() {
		log.info("==================================================");
		log.info(" 🏥 HOSPITAL PATIENT SEARCH SYSTEM STARTED ");
		log.info("==================================================");
	}

	private void printFooter() {
		log.info("==================================================");
		log.info(" ✅ APPLICATION EXECUTION COMPLETED ");
		log.info("==================================================");
	}
}
