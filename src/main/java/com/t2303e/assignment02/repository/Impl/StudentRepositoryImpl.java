package com.t2303e.assignment02.repository.Impl;

import com.t2303e.assignment02.entity.Student;
import com.t2303e.assignment02.repository.StudentRepository;
import com.t2303e.assignment02.repository.generic.AbstractGenericRepository;

public class StudentRepositoryImpl extends AbstractGenericRepository<Student> implements StudentRepository {
    public StudentRepositoryImpl() {
        super(Student.class);
    }
}
