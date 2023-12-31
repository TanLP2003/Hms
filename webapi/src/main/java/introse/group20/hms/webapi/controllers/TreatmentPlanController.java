package introse.group20.hms.webapi.controllers;

import introse.group20.hms.application.services.interfaces.ITreatmentPlanService;
import introse.group20.hms.core.entities.TreatmentPlan;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.core.exceptions.NotFoundException;
import introse.group20.hms.webapi.DTOs.TreatmentPlanDTO.TreatmentPlanRequest;
import introse.group20.hms.webapi.DTOs.TreatmentPlanDTO.TreatmentPlanResponse;
import introse.group20.hms.webapi.utils.AuthExtensions;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
@RequestMapping("/api/treatment_plans")
@Validated
public class TreatmentPlanController {
    @Autowired
    private ITreatmentPlanService treatmentPlanService;
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping("/patient")
    @Secured({"DOCTOR", "PATIENT"})
    //route: /api/treatment_plans/patient?patientId=<if of patient>
    public ResponseEntity<List<TreatmentPlanResponse>> getForPatient(@RequestParam UUID patientId){
        List<TreatmentPlan> treatmentPlans = treatmentPlanService.getForUser(patientId);
        List<TreatmentPlanResponse> treatmentPlanResponses = treatmentPlans.stream()
                .map(treatmentPlan -> modelMapper.map(treatmentPlan, TreatmentPlanResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<List<TreatmentPlanResponse>>(treatmentPlanResponses, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", name = "getById")
    @Secured({"DOCTOR", "PATIENT"})
    public ResponseEntity<TreatmentPlanResponse> getById(@PathVariable UUID id) throws NotFoundException {
        TreatmentPlan treatmentPlan = treatmentPlanService.getById(id);
        TreatmentPlanResponse treatmentPlanResponse = modelMapper.map(treatmentPlan, TreatmentPlanResponse.class);
        return new ResponseEntity<>(treatmentPlanResponse, HttpStatus.OK);
    }

    @PostMapping
    @Secured("DOCTOR")
    public ResponseEntity<TreatmentPlanResponse> createTreatmentPlan(@Valid @RequestBody TreatmentPlanRequest request) throws BadRequestException {
        UUID doctorId = AuthExtensions.GetUserIdFromContext(SecurityContextHolder.getContext());
        TreatmentPlan treatmentPlan = modelMapper.map(request, TreatmentPlan.class);
        TreatmentPlan newTreatmentPlan = treatmentPlanService.createTreatmentPlan(request.getPatientId(), doctorId, treatmentPlan);
        TreatmentPlanResponse treatmentPlanResponse = modelMapper.map(newTreatmentPlan, TreatmentPlanResponse.class);
        return new ResponseEntity<>(treatmentPlanResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Secured("DOCTOR")
    public ResponseEntity<TreatmentPlanResponse> updateTreatmentPlan(@PathVariable UUID id, @Valid @RequestBody TreatmentPlanRequest treatmentPlanRequest) throws BadRequestException {
        TreatmentPlan treatmentPlan = modelMapper.map(treatmentPlanRequest, TreatmentPlan.class);
        treatmentPlan.setId(id);
        TreatmentPlan updatedTreatmentPlan = treatmentPlanService.updateTreatmentPlan(treatmentPlan);
        TreatmentPlanResponse treatmentPlanResponse = modelMapper.map(updatedTreatmentPlan, TreatmentPlanResponse.class);
        return new ResponseEntity<>(treatmentPlanResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Secured("DOCTOR")
    public ResponseEntity<HttpStatus> deleteTreatmentPlan(@PathVariable UUID id) throws BadRequestException {
        treatmentPlanService.deleteTreatmentPlan(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
