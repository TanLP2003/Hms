package introse.group20.hms;

import introse.group20.hms.infracstructure.models.CategoryModel;
import introse.group20.hms.infracstructure.repositories.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoriesSeeder implements CommandLineRunner {
    @Autowired
    private ICategoryRepository categoryRepository;
    @Override
    public void run(String... args) throws Exception {
        List<CategoryModel> categoryModels = new ArrayList<>();
        categoryModels.add(new CategoryModel("#HealthcareTips"));
        categoryModels.add(new CategoryModel("#MedicalAdvice"));
        categoryModels.add(new CategoryModel("#HealthNews"));
        categoryModels.add(new CategoryModel("#HealthyLiving"));
        categoryModels.add(new CategoryModel("#MedicalResearch"));
        categoryModels.add(new CategoryModel("#WellnessTips"));
        categoryModels.add(new CategoryModel("#MedicineUpdates"));
        categoryModels.add(new CategoryModel("#DiseasePrevention"));
        categoryModels.add(new CategoryModel("#FitnessMotivation"));
        categoryModels.add(new CategoryModel("#MentalHealthAwareness"));
        categoryModels.add(new CategoryModel("#NutritionFacts"));
        categoryModels.add(new CategoryModel("#HealthyHabits"));
        categoryModels.add(new CategoryModel("#MedicalBreakthroughs"));
        categoryModels.add(new CategoryModel("#HealthEducation"));
        categoryModels.add(new CategoryModel("#PatientCare"));
        categoryModels.add(new CategoryModel("#HealthTech"));
        categoryModels.add(new CategoryModel("#MedicalScience"));
        categoryModels.add(new CategoryModel("#HolisticHealth"));
        categoryModels.add(new CategoryModel("#MedicalInnovation"));
        categoryModels.add(new CategoryModel("#SelfCareJourney"));
        categoryRepository.saveAll(categoryModels);
    }
}
