package com.nrapendra.course;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nrapendra.student.Student;
import com.nrapendra.teacher.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "course")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "courseName",nullable = false)
    private String courseName;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "published",nullable = false)
    private String published;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;

}
