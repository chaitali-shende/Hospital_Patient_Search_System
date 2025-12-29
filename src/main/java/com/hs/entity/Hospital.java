package com.hs.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "hospital")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospital {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hospitalId;

	@NonNull
	private String hospitalName;

	@NonNull
	private String location;

	// One Hospital → Many Doctors
	@OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
	private List<Doctor> doctors;

	// One Hospital → Many Patients
	@OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
	private List<Patient> patients;

}
