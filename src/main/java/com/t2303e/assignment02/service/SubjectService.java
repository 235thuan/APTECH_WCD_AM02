package com.t2303e.assignment02.service;

import com.t2303e.assignment02.entity.Student;
import com.t2303e.assignment02.entity.Subject;
import com.t2303e.assignment02.repository.Impl.StudentRepositoryImpl;
import com.t2303e.assignment02.repository.Impl.SubjectRepositoryImpl;
import com.t2303e.assignment02.repository.SubjectRepository;
import com.t2303e.assignment02.service.generic.AbstractGenericService;

public class SubjectService extends AbstractGenericService<Subject> {
    public SubjectService() {
        super(new SubjectRepositoryImpl());
    }
}
