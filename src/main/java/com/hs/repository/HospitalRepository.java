package com.hs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hs.entity.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
	boolean existsByHospitalName(String hospitalName);
}
