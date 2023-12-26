package introse.group20.hms.application.services;

import introse.group20.hms.application.adapters.ITreatmentPlanAdapter;
import introse.group20.hms.application.services.interfaces.ITreatmentPlanService;
import introse.group20.hms.core.entities.MedicalRecord;
import introse.group20.hms.core.entities.TreatmentPlan;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.core.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TreatmentPlanService implements ITreatmentPlanService {
    private ITreatmentPlanAdapter iTreatmentPlanAdapter;
    public TreatmentPlanService(ITreatmentPlanAdapter iTreatmentPlanAdapter)
    {
        this.iTreatmentPlanAdapter = iTreatmentPlanAdapter;
    }
    @Override
    public List<TreatmentPlan> getForUser(UUID userId)
    {
        return iTreatmentPlanAdapter.getForUserAdapter(userId);
    }

    @Override
    public TreatmentPlan getById(UUID id) throws NotFoundException {
        Optional<TreatmentPlan> treatmentPlan = iTreatmentPlanAdapter.getByIdAdapter(id);
        if(treatmentPlan.isEmpty()){
            throw new NotFoundException("Treatment Plan is not found!");
        }
        return treatmentPlan.get();
    }

    @Override
    public void createTreatmentPlan(UUID patientId, UUID doctorId, TreatmentPlan treatmentPlan) throws BadRequestException {
        iTreatmentPlanAdapter.createTreatmentPlanAdapter(patientId, doctorId, treatmentPlan);
    }

    @Override
    public void deleteTreatmentPlan(UUID id) throws BadRequestException {
        iTreatmentPlanAdapter.deleteTreatmentPlanAdapter(id);
    }

    @Override
    public void updateTreatmentPlan(TreatmentPlan treatmentPlan) throws BadRequestException {
        iTreatmentPlanAdapter.updateTreatmentPlanAdapter(treatmentPlan);
    }
}
