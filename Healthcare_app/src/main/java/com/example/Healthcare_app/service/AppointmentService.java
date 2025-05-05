package com.example.Healthcare_app.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Healthcare_app.dto.AppointmentReq;
import com.example.Healthcare_app.entity.Appointment;
import com.example.Healthcare_app.entity.Doctor;
import com.example.Healthcare_app.entity.User;
import com.example.Healthcare_app.repository.AppointmentRepository;
import com.example.Healthcare_app.repository.DoctorRepository;
import com.example.Healthcare_app.repository.UserRepository;

@Service
public class AppointmentService {
	
	private AppointmentRepository appointmentRepository;
	
	private UserRepository userRepository;
	
	private DoctorRepository doctorRepository;
	
     public AppointmentService(AppointmentRepository appointmentRepository,UserRepository userRepository,
    		 DoctorRepository doctorRepository,ModelMapper modelMapper) {
		this.appointmentRepository = appointmentRepository;
		this.userRepository = userRepository;
		this.doctorRepository=doctorRepository;
	}
	
	public ResponseEntity<String> bookAppointment(AppointmentReq appointmentreq) {

		// Fetch full User and Doctor entities by ID
		 Optional<User> patientOpt = userRepository.findById(appointmentreq.getPatientId());
		    Optional<Doctor> doctorOpt = doctorRepository.findById(appointmentreq.getDoctorId());

		    if (patientOpt.isEmpty() || doctorOpt.isEmpty()) {
		        return ResponseEntity.badRequest().body("Invalid patient or doctor ID");
		    }
		    
		    Appointment appointment = Appointment.builder()
		    		.patient(patientOpt.get())
		    		.doctor(doctorOpt.get())
		    		.appointmentTime(appointmentreq.getAppointmentTime())
		    		.status(appointmentreq.getStatus())
		    		.build();
		    		

		    appointmentRepository.save(appointment);
		    return ResponseEntity.ok("Appointment booked successfully");
    }
	
	 public Appointment getAppointment(Long id) {
	  return appointmentRepository.
			  findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
	    }
	 
	 public Appointment cancelAppointment(Long id) {
	        Appointment appointment = getAppointment(id);
	        appointment.setStatus("CANCELLED");
	        return appointmentRepository.save(appointment);
	    }

}
