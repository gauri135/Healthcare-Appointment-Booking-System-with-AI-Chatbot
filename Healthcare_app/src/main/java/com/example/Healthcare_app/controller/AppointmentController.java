package com.example.Healthcare_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Healthcare_app.dto.AppointmentReq;
import com.example.Healthcare_app.entity.Appointment;
import com.example.Healthcare_app.service.AppointmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/appointments")
@Tag(name = "Appointment Controller", description = "APIs for booking, getting and canceling appointments")
public class AppointmentController {
	
	private AppointmentService appointmentService;
	
	public AppointmentController(AppointmentService appointmentService)
	{
		this.appointmentService=appointmentService;
	}
	
	
	@PostMapping
	@Operation(summary = "Book an appointment")
	public ResponseEntity<ResponseEntity<String>> bookAppointment(@RequestBody AppointmentReq appointment)
	{
		log.info("Receiving Appointment request....||{}",appointment);
		return ResponseEntity.ok(appointmentService.bookAppointment(appointment));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get appointment by ID")
	public ResponseEntity<Appointment> getAppointment(@PathVariable Long id)
	{
		log.info("Getting Appointment....");
		
		return ResponseEntity.ok(appointmentService.getAppointment(id));
	}
	
	@PutMapping("/{id}/cancel")
	 @Operation(summary = "Cancel appointment by ID")
	public ResponseEntity<Appointment> cancelAppointment(@PathVariable Long id)
	{
		log.info("Canceling Appointment.....");
		
		return ResponseEntity.ok(appointmentService.cancelAppointment(id));
		
	}

}
