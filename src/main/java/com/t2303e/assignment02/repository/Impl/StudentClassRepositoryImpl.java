package com.t2303e.assignment02.repository.Impl;

import com.t2303e.assignment02.entity.StudentClass;
import com.t2303e.assignment02.repository.StudentClassRepository;
import com.t2303e.assignment02.repository.generic.AbstractGenericRepository;
import com.t2303e.assignment02.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class StudentClassRepositoryImpl extends AbstractGenericRepository<StudentClass> implements StudentClassRepository {
    public StudentClassRepositoryImpl() {
        super(StudentClass.class);
    }
    public int countStudentsByClassId(int classId) {
        int count = 0;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "SELECT COUNT(s.id) FROM Student s WHERE s.studentClass.id = :classId";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("classId", classId);
            count = query.uniqueResult().intValue();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error in countStudentsByClassId: " + e.getMessage());
        }
        return count;
    }

}

