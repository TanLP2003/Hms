package introse.group20.hms.application.services.interfaces;

import introse.group20.hms.core.entities.TreatmentPlan;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.core.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface ITreatmentPlanService {
    List<TreatmentPlan> getForUser(UUID userId);
    TreatmentPlan getById(UUID id) throws NotFoundException;
    void createTreatmentPlan(UUID patientId,UUID doctorId, TreatmentPlan treatmentPlan) throws BadRequestException;
    void deleteTreatmentPlan(UUID id) throws BadRequestException;
    void updateTreatmentPlan(TreatmentPlan treatmentPlan) throws BadRequestException;
}
