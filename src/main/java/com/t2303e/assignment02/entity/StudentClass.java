package com.t2303e.assignment02.entity;

import com.t2303e.assignment02.repository.generic.NamedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "classes")
@Getter
@Setter
public class StudentClass implements NamedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, name = "name")
    private String name;
    @OneToMany(mappedBy = "studentClass")
    private List<Student> students;

    @Override
    public String toString() {
        return "StudentClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

