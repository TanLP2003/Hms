package introse.group20.hms.application.adapters;

import introse.group20.hms.core.entities.MedicalRecord;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.core.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMedicalRecordAdapter {
    List<MedicalRecord> getByPatientIdAdapter(UUID patientId);
    Optional<MedicalRecord> getByIdAdapter(UUID mrId) throws NotFoundException;
    MedicalRecord addMedicalRecordAdapter(UUID doctorId, UUID departmentId, UUID patientId, MedicalRecord medicalRecord) throws BadRequestException;
    void updateMedicalRecordAdapter(MedicalRecord medicalRecord) throws BadRequestException;
    void deleteMedicalRecordAdapter(UUID mrId) throws BadRequestException;
}
