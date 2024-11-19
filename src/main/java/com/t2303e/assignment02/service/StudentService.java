package com.t2303e.assignment02.service;

import com.t2303e.assignment02.entity.Student;
import com.t2303e.assignment02.repository.Impl.StudentRepositoryImpl;
import com.t2303e.assignment02.service.generic.AbstractGenericService;

public class StudentService extends AbstractGenericService<Student> {
    public StudentService() {
        super(new StudentRepositoryImpl());
    }
}
