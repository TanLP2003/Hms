package introse.group20.hms.webapi.controllers;

import introse.group20.hms.application.services.interfaces.IVoteService;
import introse.group20.hms.core.entities.Vote;
import introse.group20.hms.core.entities.enums.Rating;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.core.exceptions.NotFoundException;
import introse.group20.hms.webapi.DTOs.VoteDTO.VoteRequest;
import introse.group20.hms.webapi.DTOs.VoteDTO.VoteResponse;
import introse.group20.hms.webapi.utils.AuthExtensions;
import jakarta.validation.Valid;
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
@RequestMapping("/api/votes")
@Validated
public class VoteController {

    @Autowired
    private IVoteService voteService;

    @GetMapping("/doctor")
    //route: /api/votes/doctor?doctorId=<id of doctor>
    public ResponseEntity<List<VoteResponse>> getDoctorVotes(@RequestParam UUID doctorId) {
        List<Vote> votes = voteService.getDoctorVote(doctorId);
        List<VoteResponse> voteResponses = votes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(voteResponses, HttpStatus.OK);
    }

    @GetMapping("/{voteId}")
    public ResponseEntity<VoteResponse> getById(@PathVariable UUID voteId) throws NotFoundException {
        Vote vote = voteService.getById(voteId);
        VoteResponse voteResponse = mapToDTO(vote);
        return new ResponseEntity<>(voteResponse, HttpStatus.OK);
    }

    @PostMapping
    @Secured("PATIENT")
    public ResponseEntity<VoteResponse> addVote(@Valid @RequestBody VoteRequest voteRequest) throws BadRequestException {
        Vote vote = mapToEntity(voteRequest);
        UUID patientId = AuthExtensions.GetUserIdFromContext(SecurityContextHolder.getContext());
        Vote newVote = voteService.addVote(patientId, voteRequest.getDoctorId(), vote);
        return new ResponseEntity<>(mapToDTO(newVote), HttpStatus.CREATED);
    }

    @PutMapping("/{voteId}")
    @Secured("PATIENT")
    public ResponseEntity<HttpStatus> updateVote(@PathVariable UUID voteId, @Valid @RequestBody VoteRequest voteRequest) throws BadRequestException {
        Vote vote = mapToEntity(voteRequest);
        vote.setId(voteId);
        voteService.updateVote(vote);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{voteId}")
    @Secured("PATIENT")
    public ResponseEntity<HttpStatus> deleteVote(@PathVariable UUID voteId) throws BadRequestException {
        voteService.deleteVote(voteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private VoteResponse mapToDTO(Vote vote) {
        return new VoteResponse(vote.getId(), vote.getPatient().getId(), vote.getPatient().getName(),
                vote.getRating() == null ? 0 : vote.getRating().getValue(), vote.getContent());
    }

    private Vote mapToEntity(VoteRequest voteRequest) {
        return new Vote(Rating.values()[voteRequest.getRating() - 1], voteRequest.getContent());
    }
}
