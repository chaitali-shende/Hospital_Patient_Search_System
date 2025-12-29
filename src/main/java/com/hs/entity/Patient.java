package com.hs.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long patientId;

	@NonNull
	private String patientName;
	@NonNull
	private String disease;
	@NonNull
	private Integer age;
	@NonNull
	private LocalDate admissionDate;
	@NonNull
	private Boolean discharged;

	// Many Patients → One Hospital
	@ManyToOne
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	// Many Patients → One Doctor
	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

}
