package introse.group20.hms.infracstructure.adapters;

import introse.group20.hms.application.adapters.IVoteAdapter;
import introse.group20.hms.core.entities.Vote;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.infracstructure.models.DoctorModel;
import introse.group20.hms.infracstructure.models.PatientModel;
import introse.group20.hms.infracstructure.models.VoteModel;
import introse.group20.hms.infracstructure.repositories.IDoctorRepository;
import introse.group20.hms.infracstructure.repositories.IPatientRepository;
import introse.group20.hms.infracstructure.repositories.IVoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class VoteAdapter implements IVoteAdapter {

    @Autowired
    private IVoteRepository iVoteRepository;

    @Autowired
    private IPatientRepository iPatientRepository;

    @Autowired
    private IDoctorRepository iDoctorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Vote> getDoctorVoteAdapter(UUID doctorId) {
        List<VoteModel> voteModels = iVoteRepository.findByDoctorId(doctorId);
        return voteModels.stream()
                .map(voteModel -> modelMapper.map(voteModel, Vote.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Vote> getByIdAdapter(UUID voteId) {
        Optional<VoteModel> voteModel = iVoteRepository.findById(voteId);
        if (voteModel.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(modelMapper.map(voteModel.get(), Vote.class));
    }

    @Override
    public Vote addVoteAdapter(UUID patientId, UUID doctorId, Vote vote) throws BadRequestException {
        Optional<PatientModel> patientModel = iPatientRepository.findById(patientId);
        Optional<DoctorModel> doctorModel = iDoctorRepository.findById(doctorId);
        if (patientModel.isEmpty() || doctorModel.isEmpty()) {
            throw new BadRequestException("BAD REQUEST!");
        }
        VoteModel voteModel = modelMapper.map(vote, VoteModel.class);
        voteModel.setDoctor(doctorModel.get());
        voteModel.setPatient(patientModel.get());
        VoteModel savedVote = iVoteRepository.save(voteModel);
        return modelMapper.map(savedVote, Vote.class);
    }

    @Override
    public void updateVoteAdapter(Vote vote) throws BadRequestException {
        Optional<VoteModel> voteModel = iVoteRepository.findById(vote.getId());
        if (voteModel.isEmpty()) {
            throw new BadRequestException("BAD REQUEST!");
        }
        VoteModel updatedVote = voteModel.get();

        updatedVote.setRating(vote.getRating());
        updatedVote.setContent(vote.getContent());
        iVoteRepository.save(updatedVote);
    }

    @Override
    public void deleteVoteAdapter(UUID voteId) throws BadRequestException {
        Optional<VoteModel> voteModel = iVoteRepository.findById(voteId);
        if (voteModel.isEmpty()) {
            throw new BadRequestException("BAD REQUEST!");
        }
        iVoteRepository.deleteById(voteId);
    }
}
