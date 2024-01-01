package introse.group20.hms.webapi.controllers;

import introse.group20.hms.application.services.interfaces.ISurgeryService;
import introse.group20.hms.core.entities.Surgery;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.webapi.DTOs.PrescriptionDTO.PrescriptionRequest;
import introse.group20.hms.webapi.DTOs.SurgeryDTO.SurgeryRequest;
import introse.group20.hms.webapi.DTOs.SurgeryDTO.SurgeryResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/surgeries")
public class SurgeryController {
    @Autowired
    private ISurgeryService surgeryService;
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping("/")
    public List<SurgeryResponse> getAll()
    {
        List<Surgery> surgeries= surgeryService.getAll();
        return surgeries.stream()
                .map(surgery -> modelMapper.map(surgery, SurgeryResponse.class) )
                .collect(Collectors.toList());
    }

    @GetMapping("/doctor")
    @Secured({"DOCTOR", "ADMIN"})
    // route: /api/surgeries/doctor?doctorId=<id of doctor>
    public ResponseEntity<List<SurgeryResponse>> getSurgeryForDoctor(@RequestParam UUID doctorId){
        List<Surgery> surgeries = surgeryService.getSurgeryForDoctor(doctorId);
        List<SurgeryResponse> surgeryResponses = surgeries.stream()
                .map(surgery -> modelMapper.map(surgery, SurgeryResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<List<SurgeryResponse>>(surgeryResponses, HttpStatus.OK);
    }

    @PostMapping
    @Secured("ADMIN")
    public ResponseEntity<SurgeryResponse> addSurgery(@Valid @RequestBody SurgeryRequest surgeryRequest) throws BadRequestException {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Surgery surgery=modelMapper.map(surgeryRequest,Surgery.class);
        Surgery surgery1 = surgeryService.addSurgery(surgeryRequest.getDoctorId(),surgeryRequest.getPatientId(),surgery);
        SurgeryResponse surgeryResponse=modelMapper.map(surgery1,SurgeryResponse.class);
        return new ResponseEntity<SurgeryResponse>(surgeryResponse,HttpStatus.CREATED);
    }

    @PutMapping("/{surgeryId}")
    @Secured("ADMIN")
    public ResponseEntity<HttpStatus> updateSurgery(@PathVariable UUID surgeryId, @Valid @RequestBody SurgeryRequest surgeryRequest){
        Surgery surgery = modelMapper.map(surgeryRequest, Surgery.class);
        surgery.setId(surgeryId);
        surgeryService.updateSurgery(surgery);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    @DeleteMapping("/{surgeryId}")
    @Secured("ADMIN")
    public ResponseEntity<HttpStatus> deleteSurgery(@PathVariable UUID surgeryId){
        surgeryService.deleteSurge(surgeryId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
