package introse.group20.hms.webapi.controllers;

import introse.group20.hms.application.services.interfaces.IPatientService;
import introse.group20.hms.application.services.interfaces.ISurgeryService;
import introse.group20.hms.core.entities.Surgery;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.webapi.DTOs.PrescriptionDTO.PrescriptionRequest;
import introse.group20.hms.webapi.DTOs.SurgeryDTO.SurgeryRequest;
import introse.group20.hms.webapi.DTOs.SurgeryDTO.SurgeryResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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
                .map(this::mapToSurgeryResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/doctor")
    @Secured({"DOCTOR", "ADMIN"})
    // route: /api/surgeries/doctor?doctorId=<id of doctor>
    public ResponseEntity<List<SurgeryResponse>> getSurgeryForDoctor(@RequestParam UUID doctorId){
        List<SurgeryResponse> surgeryResponses = surgeryService.getSurgeryForDoctor(doctorId).stream()
                .map(this::mapToSurgeryResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(surgeryResponses);
    }

    @PostMapping
    @Secured("ADMIN")
    public ResponseEntity<SurgeryResponse> addSurgery(@Valid @RequestBody SurgeryRequest surgeryRequest) throws BadRequestException {
        Surgery newSurgery = mapToSurgery(surgeryRequest);
        Surgery savedSurgery = surgeryService.addSurgery(surgeryRequest.getDoctorId(), surgeryRequest.getPatientId(), newSurgery);
        return ResponseEntity.ok(mapToSurgeryResponse(savedSurgery));
    }

    @PutMapping("/{surgeryId}")
    @Secured("ADMIN")
    public ResponseEntity<HttpStatus> updateSurgery(@PathVariable UUID surgeryId, @Valid @RequestBody SurgeryRequest surgeryRequest) throws BadRequestException {
        Surgery surgery = mapToSurgery(surgeryRequest);
        surgery.setId(surgeryId);
        surgeryService.updateSurgery(surgery);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{surgeryId}")
    @Secured("ADMIN")
    public ResponseEntity<HttpStatus> deleteSurgery(@PathVariable UUID surgeryId) throws BadRequestException {
        surgeryService.deleteSurgery(surgeryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    private SurgeryResponse mapToSurgeryResponse(Surgery surgery){
        return new SurgeryResponse(
            surgery.getId(),
                surgery.getDoctor().getId(),
                surgery.getDoctor().getName(),
                surgery.getPatient().getId(),
                surgery.getPatient().getName(),
                surgery.getTime(),
                surgery.getContent(),
                surgery.getExpectedTime()
        );
    }
    private Surgery mapToSurgery(SurgeryRequest surgeryRequest){
        Surgery surgery = new Surgery();
        surgery.setContent(surgeryRequest.getContent());
        surgery.setTime(surgeryRequest.getTime());
        surgery.setExpectedTime(surgeryRequest.getExpectedTime());
        return surgery;
    }
}
