package com.t2303e.assignment02.entity;

import com.t2303e.assignment02.repository.generic.NamedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subjects")
@Getter
@Setter
public class Subject implements NamedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int hours;
    @ManyToMany(mappedBy = "subjects")
    private List<Student> students;
}
