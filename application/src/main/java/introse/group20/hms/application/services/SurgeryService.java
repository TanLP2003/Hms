package introse.group20.hms.application.services;

import introse.group20.hms.application.adapters.ISurgeryAdapter;
import introse.group20.hms.application.services.interfaces.ISurgeryService;
import introse.group20.hms.core.entities.Surgery;
import introse.group20.hms.core.exceptions.BadRequestException;

import java.util.List;
import java.util.UUID;

public class SurgeryService implements ISurgeryService {
    private ISurgeryAdapter surgeryAdapter;
    public SurgeryService(ISurgeryAdapter surgeryAdapter)
    {
        this.surgeryAdapter = surgeryAdapter;
    }
    @Override
    public List<Surgery> getAll()
    {
        return surgeryAdapter.getAllAdapter();
    }

    @Override
    public List<Surgery> getSurgeryForDoctor(UUID doctorId)
    {
        return surgeryAdapter.getSurgeryForDoctorAdapter(doctorId);
    }

    @Override
    public Surgery addSurgery(UUID doctorId, UUID patientId, Surgery surgery) throws BadRequestException {
        return surgeryAdapter.addSurgeryAdapter(doctorId, patientId, surgery);
    }

    @Override
    public void updateSurgery(Surgery surgery) {
        surgeryAdapter.updateSurgeryAdapter(surgery);
    }

    @Override
    public void deleteSurge(UUID surgeryId) {
        surgeryAdapter.deleteSurgeAdapter(surgeryId);
    }
}
