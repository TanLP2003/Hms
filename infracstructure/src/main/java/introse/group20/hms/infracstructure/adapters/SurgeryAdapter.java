package introse.group20.hms.infracstructure.adapters;

import introse.group20.hms.application.adapters.ISurgeryAdapter;
import introse.group20.hms.core.entities.Doctor;
import introse.group20.hms.core.entities.Patient;
import introse.group20.hms.core.entities.Surgery;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.infracstructure.models.DoctorModel;
import introse.group20.hms.infracstructure.models.PatientModel;
import introse.group20.hms.infracstructure.models.SurgeryModel;
import introse.group20.hms.infracstructure.repositories.IDoctorRepository;
import introse.group20.hms.infracstructure.repositories.IPatientRepository;
import introse.group20.hms.infracstructure.repositories.ISurgeryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Component
public class SurgeryAdapter implements ISurgeryAdapter {
    @Autowired
    private ISurgeryRepository surgeryRepository;
    @Autowired
    private IDoctorRepository doctorRepository;
    @Autowired
    private IPatientRepository patientRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<Surgery> getAllAdapter() {
        List<SurgeryModel> surgeryModels = surgeryRepository.findAll();
        return surgeryModels.stream()
                .map(surgeryModel -> modelMapper.map(surgeryModel, Surgery.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Surgery> getSurgeryForDoctorAdapter(UUID doctorId)
    {
        List<SurgeryModel> surgeryModels = surgeryRepository.findByDoctorId(doctorId);
        return surgeryModels.stream()
                .map(surgeryModel -> modelMapper.map(surgeryModel, Surgery.class))
                .collect(Collectors.toList());
    }

    @Override
    public Surgery addSurgeryAdapter(UUID doctorId, UUID patientId, Surgery surgery) throws BadRequestException {
        SurgeryModel surgeryModel = modelMapper.map(surgery, SurgeryModel.class);
        Optional<DoctorModel> doctorModel=doctorRepository.findById(doctorId);
        Optional<PatientModel> patientModel=patientRepository.findById(patientId);
        if(patientModel.isEmpty() || doctorModel.isEmpty()){
            throw new BadRequestException("Bad request");
        }
        surgeryModel.setDoctor(doctorModel.get());
        surgeryModel.setPatient(patientModel.get());
        surgeryRepository.save(surgeryModel);
        Surgery surgery1=modelMapper.map(surgeryModel,Surgery.class);
        return surgery1;
    }

    @Override
    public void updateSurgeryAdapter(Surgery surgery) {
        SurgeryModel surgeryModel = modelMapper.map(surgery, SurgeryModel.class);
        surgeryRepository.save(surgeryModel);
    }

    @Override
    public void deleteSurgeAdapter(UUID surgeryId) {
        surgeryRepository.deleteById(surgeryId);
    }
}
