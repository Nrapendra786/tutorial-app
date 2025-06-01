package com.nrapendra.teacher;

import com.nrapendra.course.Course;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "teacher")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teacher_name",nullable = false)
    private String teacherName;

}
