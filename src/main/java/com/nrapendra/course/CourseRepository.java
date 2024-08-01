package com.nrapendra.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findByPublished(String published);
    Optional<Course> findByCourseName(String courseName);
}
