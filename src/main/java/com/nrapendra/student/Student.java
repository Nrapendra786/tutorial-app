package com.nrapendra.student;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nrapendra.course.Course;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "student")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Course> courses;
}
