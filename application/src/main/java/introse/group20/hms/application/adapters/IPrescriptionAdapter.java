package introse.group20.hms.application.adapters;

import introse.group20.hms.core.entities.Prescription;
import introse.group20.hms.core.exceptions.BadRequestException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPrescriptionAdapter {
    List<Prescription> getByPatientAdapter(UUID patientId);
    Optional<Prescription> getByIdAdapter(UUID presId);
    Prescription addPrescriptionAdapter(UUID doctorId, UUID patientId, Prescription prescription) throws BadRequestException;
    void updatePrescriptionAdapter(Prescription prescription) throws BadRequestException;
    void deletePrescriptionAdapter(UUID presId) throws BadRequestException;
}
