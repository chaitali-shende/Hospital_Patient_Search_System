package com.hs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hs.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}