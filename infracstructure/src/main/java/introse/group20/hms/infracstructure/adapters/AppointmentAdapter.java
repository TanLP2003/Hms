package introse.group20.hms.infracstructure.adapters;

import introse.group20.hms.application.adapters.IAppointmentAdapter;
import introse.group20.hms.core.entities.Appointment;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.infracstructure.models.AppointmentModel;
import introse.group20.hms.infracstructure.models.DoctorModel;
import introse.group20.hms.infracstructure.models.PatientModel;
import introse.group20.hms.infracstructure.repositories.IAppointmentRepository;
import introse.group20.hms.infracstructure.repositories.IDoctorRepository;
import introse.group20.hms.infracstructure.repositories.IPatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AppointmentAdapter implements IAppointmentAdapter {
    @Autowired
    private IAppointmentRepository iAppointmentRepository;
    @Autowired
    private IDoctorRepository doctorRepository;
    @Autowired
    private IPatientRepository patientRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Appointment getAppointmentById(UUID appointmentId) {
        Optional<AppointmentModel> appointmentModel=iAppointmentRepository.findById(appointmentId);
        Appointment appointment=modelMapper.map(appointmentModel,Appointment.class);
        return appointment;
    }

    @Override
    public List<Appointment> getAppointmentByDoctorAdapter(UUID doctorId) {
        List<AppointmentModel> appointmentModels= iAppointmentRepository.findByDoctorId(doctorId);
        return appointmentModels.stream()
                .map(appointmentModel -> modelMapper.map(appointmentModel,Appointment.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getAppointmentByPatientAdapter(UUID patientId) {
        List<AppointmentModel> appointmentModels= iAppointmentRepository.findByPatientId(patientId);
        return appointmentModels.stream()
                .map(appointmentModel -> modelMapper.map(appointmentModel,Appointment.class))
                .collect(Collectors.toList());
    }

    @Override
    public Appointment addAppointmentAdapter(UUID doctorId, UUID patientId, Appointment apm) throws BadRequestException {
        AppointmentModel appointmentModel=modelMapper.map(apm,AppointmentModel.class);
        Optional<DoctorModel> doctorModel=doctorRepository.findById(doctorId);
        Optional<PatientModel> patientModel=patientRepository.findById(patientId);
        if(patientModel.isEmpty()|| doctorModel.isEmpty()){
            throw new BadRequestException("Bad request!");
        }
        appointmentModel.setDoctor(doctorModel.get());
        appointmentModel.setPatient(patientModel.get());
        iAppointmentRepository.save(appointmentModel);
        return apm;
    }

    @Override
    public void updateAppointmentAdapter(Appointment appointment) throws BadRequestException {
        AppointmentModel appointmentModel=modelMapper.map(appointment,AppointmentModel.class);
        if(!iAppointmentRepository.existsById(appointment.getId())){
            throw new BadRequestException("Bad request!");
        }
        Optional<AppointmentModel> appointmentModel1=iAppointmentRepository.findById(appointment.getId());
        appointmentModel.setPatient(appointmentModel1.get().getPatient());
        iAppointmentRepository.save(appointmentModel);
    }

    @Override
    public void deleteAppointmentAdapter(UUID id) {
        iAppointmentRepository.deleteById(id);
    }
}
