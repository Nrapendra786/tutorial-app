package com.nrapendra.teacher;

import com.nrapendra.course.Course;
import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "teacher_name",nullable = false)
    private String teacherName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="course_id")
    private Course course;

}
