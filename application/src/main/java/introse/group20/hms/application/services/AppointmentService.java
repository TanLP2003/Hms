package introse.group20.hms.application.services;

import introse.group20.hms.application.adapters.IAppointmentAdapter;
import introse.group20.hms.application.services.interfaces.IAppointmentService;
import introse.group20.hms.core.entities.Appointment;
import introse.group20.hms.core.exceptions.BadRequestException;

import java.util.List;
import java.util.UUID;

public class AppointmentService implements IAppointmentService {
    private IAppointmentAdapter iAppointmentAdapter;
    public AppointmentService(IAppointmentAdapter iAppointmentAdapter){
        this.iAppointmentAdapter=iAppointmentAdapter;
    }
    @Override
    public Appointment getAppointmentById(UUID appointmentId) {
        return iAppointmentAdapter.getAppointmentById(appointmentId);
    }
    @Override
    public List<Appointment> getAppointmentByDoctor(UUID doctorId) {
        return iAppointmentAdapter.getAppointmentByDoctorAdapter(doctorId);
    }

    @Override
    public List<Appointment> getAppointmentByPatient(UUID patientId) {
        return iAppointmentAdapter.getAppointmentByPatientAdapter(patientId);
    }

    @Override
    public Appointment addAppointment(UUID doctorId, UUID patientId, Appointment apm) throws BadRequestException {
        return iAppointmentAdapter.addAppointmentAdapter(doctorId,patientId,apm);
    }

    @Override
    public void updateAppointment(Appointment appointment) throws BadRequestException {
        iAppointmentAdapter.updateAppointmentAdapter(appointment);
    }

    @Override
    public void deleteAppointment(UUID id) {
        iAppointmentAdapter.deleteAppointmentAdapter(id);
    }
}
