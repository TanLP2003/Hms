package introse.group20.hms.infracstructure.adapters;

import introse.group20.hms.application.adapters.ITreatmentPlanAdapter;
import introse.group20.hms.core.entities.TreatmentPlan;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.infracstructure.models.DoctorModel;
import introse.group20.hms.infracstructure.models.PatientModel;
import introse.group20.hms.infracstructure.models.TreatmentPlanModel;
import introse.group20.hms.infracstructure.repositories.IDoctorRepository;
import introse.group20.hms.infracstructure.repositories.IPatientRepository;
import introse.group20.hms.infracstructure.repositories.ITreatmentPlanRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TreatmentPlanAdapter implements ITreatmentPlanAdapter {
    @Autowired
    private ITreatmentPlanRepository treatmentPlanRepository;
    @Autowired
    private IPatientRepository patientRepository;
    @Autowired
    private IDoctorRepository doctorRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<TreatmentPlan> getForUserAdapter(UUID userId) {
        List<TreatmentPlanModel> treatmentPlanModels = treatmentPlanRepository.findByPatientId(userId);
        return treatmentPlanModels.stream()
                .map(treatmentPlanModel -> modelMapper.map(treatmentPlanModel, TreatmentPlan.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TreatmentPlan> getByIdAdapter(UUID id) {
        return treatmentPlanRepository.findById(id)
                .map(treatmentPlanModel -> modelMapper.map(treatmentPlanModel, TreatmentPlan.class));
    }

    @Override
    public TreatmentPlan createTreatmentPlanAdapter(UUID patientId, UUID doctorId, TreatmentPlan treatmentPlan) throws BadRequestException {
        TreatmentPlanModel treatmentPlanModel = modelMapper.map(treatmentPlan, TreatmentPlanModel.class);
        Optional<PatientModel> patientModel = patientRepository.findById(patientId);
        Optional<DoctorModel> doctorModel = doctorRepository.findById(doctorId);
        if (patientModel.isEmpty() || doctorModel.isEmpty())
        {
            throw new BadRequestException("Bad request!");
        }
        treatmentPlanModel.setDoctor(doctorModel.get());
        treatmentPlanModel.setPatient(patientModel.get());
        return modelMapper.map(treatmentPlanRepository.save(treatmentPlanModel), TreatmentPlan.class);
    }

    @Override
    public void updateTreatmentPlanAdapter(UUID userId, TreatmentPlan treatmentPlan) throws BadRequestException {
        TreatmentPlanModel treatmentPlanModel = treatmentPlanRepository.findById(treatmentPlan.getId())
                .orElseThrow(() -> new BadRequestException("TreatmentPlan not exist"));
        if(userId.compareTo(treatmentPlanModel.getDoctor().getId()) != 0) {
            throw new BadRequestException("Bad Request! Action not allowed!");
        }
        treatmentPlanModel.setTreatmentMethod(treatmentPlan.getTreatmentMethod());
        treatmentPlanModel.setLastExaminationDay(treatmentPlan.getLastExaminationDay());
        treatmentPlanModel.setNextExpectedExaminationDay(treatmentPlan.getNextExpectedExaminationDay());
        treatmentPlanModel.setNote(treatmentPlan.getNote());
        treatmentPlanRepository.save(treatmentPlanModel);
    }

    @Override
    public void deleteTreatmentPlanAdapter(UUID userId, UUID id) throws BadRequestException {
        TreatmentPlanModel treatmentPlanModel = treatmentPlanRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("TreatmentPlan not exist"));
        if(userId.compareTo(treatmentPlanModel.getDoctor().getId()) != 0) {
            throw new BadRequestException("Bad Request! Action not allowed!");
        }
        treatmentPlanRepository.delete(treatmentPlanModel);
    }
}
