package introse.group20.hms.application.services;

import introse.group20.hms.application.adapters.IAppointmentAdapter;
import introse.group20.hms.application.services.interfaces.IAppointmentService;
import introse.group20.hms.core.entities.Appointment;
import introse.group20.hms.core.entities.enums.AppointmentStatus;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.core.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class AppointmentService implements IAppointmentService {
    private IAppointmentAdapter appointmentAdapter;
    public AppointmentService(IAppointmentAdapter appointmentAdapter){this.appointmentAdapter = appointmentAdapter;}

    @Override
    public Appointment getById(UUID id) throws NotFoundException {
        return appointmentAdapter.getAppointmentById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Appointment with id: %s not exist", id)));
    }

    @Override
    public List<Appointment> getAppointmentByDoctor(UUID doctorId, int pageNo, int pageSize) {
        return appointmentAdapter.getAppointmentByDoctorAdapter(doctorId, pageNo, pageSize);
    }

    @Override
    public List<Appointment> getAppointmentByPatient(UUID patientId, int pageNo, int pageSize) {
        return appointmentAdapter.getAppointmentByPatientAdapter(patientId, pageNo, pageSize);
    }

    @Override
    public Appointment addAppointment(UUID doctorId, UUID patientId, Appointment apm) throws BadRequestException {
        return appointmentAdapter.addAppointmentAdapter(doctorId, patientId, apm);
    }

    @Override
    public void updateAppointment(UUID userId, Appointment appointment) throws BadRequestException {
        appointmentAdapter.updateAppointmentAdapter(userId, appointment);
    }

    @Override
    public void deleteAppointment(UUID userId, UUID id) throws BadRequestException {
        appointmentAdapter.deleteAppointmentAdapter(userId, id);
    }
}
