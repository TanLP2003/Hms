package introse.group20.hms.application.adapters;

import introse.group20.hms.core.entities.Doctor;
import introse.group20.hms.core.entities.User;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.core.exceptions.NotFoundException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDoctorAdapter {
    List<Doctor> getAllDoctorsAdapter(int pageNo, int pageSize);
    List<Doctor> getByDepartmentIdAdapter(UUID departmentId, int pageNo, int pageSize);
    Optional<Doctor> getByIdAdapter(UUID id) ;
    User addDoctorAdapter(UUID departmentId, Doctor doctor) throws BadRequestException;
    void updateDoctorAdapter(Doctor doctor) throws BadRequestException;
    void deleteDoctorAdapter(UUID doctorId) throws BadRequestException;
    void test();
}
