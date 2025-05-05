package com.example.Healthcare_app.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AppointmentReq {
	private Long patientId;
	private Long doctorId;
	private LocalDateTime appointmentTime;
	private String status;

}
