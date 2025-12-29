package com.hs.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hs.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByDisease(String disease);
    List<Patient> findByAdmissionDateAfter(LocalDate date);
    List<Patient> findByDischargedFalse();
}