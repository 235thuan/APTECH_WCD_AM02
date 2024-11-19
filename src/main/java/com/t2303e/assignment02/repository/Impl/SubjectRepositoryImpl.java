package com.t2303e.assignment02.repository.Impl;

import com.t2303e.assignment02.entity.Subject;
import com.t2303e.assignment02.repository.SubjectRepository;
import com.t2303e.assignment02.repository.generic.AbstractGenericRepository;


public class SubjectRepositoryImpl extends AbstractGenericRepository<Subject> implements SubjectRepository {
    public SubjectRepositoryImpl() {
        super(Subject.class);
    }
}
