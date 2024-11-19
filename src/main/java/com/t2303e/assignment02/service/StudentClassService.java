package com.t2303e.assignment02.service;
import com.t2303e.assignment02.entity.StudentClass;
import com.t2303e.assignment02.repository.Impl.StudentClassRepositoryImpl;
import com.t2303e.assignment02.repository.StudentClassRepository;
import com.t2303e.assignment02.service.generic.AbstractGenericService;

import java.util.List;

// Lớp StudentClassService cung cấp các dịch vụ liên quan đến đối tượng StudentClass.
public class StudentClassService extends AbstractGenericService<StudentClass> {
    public StudentClassService() {
        super(new StudentClassRepositoryImpl());
    }

    public int getStudentCountByClassId(int id) {
    }
}
