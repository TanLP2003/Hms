package introse.group20.hms.webapi.controllers;

import introse.group20.hms.application.services.interfaces.IAppointmentService;
import introse.group20.hms.core.entities.Appointment;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.webapi.DTOs.AppointmentDTO.AppointmentRequest;
import introse.group20.hms.webapi.DTOs.AppointmentDTO.AppointmentResponse;
import introse.group20.hms.webapi.utils.AuthExtensions;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
@Validated
public class AppointmentController {
    @Autowired
    private IAppointmentService appointmentService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/doctor", name = "getForDoctor")
    @Secured("DOCTOR")
    //route" /api/appointments/doctor
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsOfDoctor(@RequestParam UUID doctorId){
        List<Appointment> appointments=appointmentService.getAppointmentByDoctor(doctorId);
        List<AppointmentResponse> appointmentResponses= appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<List<AppointmentResponse>>(appointmentResponses,HttpStatus.OK);
    }

    @GetMapping(value = "/patient", name = "getForPatient")
    @Secured("PATIENT")
    //route" /api/appointments/patient
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsOfPatient(@RequestParam UUID patientId){
        List<Appointment> appointments=appointmentService.getAppointmentByPatient(patientId);
        List<AppointmentResponse> appointmentResponses= appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<List<AppointmentResponse>>(appointmentResponses,HttpStatus.OK);
    }

    @PostMapping("/")
    @Secured("PATIENT")
    public ResponseEntity<HttpStatus> createAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest) throws BadRequestException {
        UUID patientId= AuthExtensions.GetUserIdFromContext(SecurityContextHolder.getContext());
        Appointment appointment=modelMapper.map(appointmentRequest,Appointment.class);
        appointmentService.addAppointment(appointmentRequest.getDoctorId(),patientId,appointment);
        return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
    }

    @PutMapping("/{appointmentId}")
    @Secured({"DOCTOR", "PATIENT"})
    public ResponseEntity<HttpStatus> updateAppointment(@PathVariable UUID appointmentId, @Valid @RequestBody AppointmentRequest appointmentRequest) throws BadRequestException {
        Appointment appointment=modelMapper.map(appointmentRequest,Appointment.class);
        appointment.setId(appointmentId);
        appointmentService.updateAppointment(appointment);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    @DeleteMapping("/{appointmentId}")
    @Secured({"PATIENT"})
    public ResponseEntity<HttpStatus> deleteAppointment(@PathVariable UUID appointmentId){
        appointmentService.deleteAppointment(appointmentId);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
