package com.nrapendra.student;


import com.nrapendra.commons.exceptions.NotFoundException;
import com.nrapendra.course.Course;
import com.nrapendra.course.CourseRepository;
import com.nrapendra.teacher.Teacher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/students")
@Slf4j
@AllArgsConstructor
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{id}")
    ResponseEntity<Student> findOne(@PathVariable("id") Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND,
                        "Not found student with id = " + id));
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<Optional<Student>> update(@PathVariable("id") Long id, @RequestBody Student student) {
        try {
            studentRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND,"student is not found"));
            studentRepository.saveAndFlush(student);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    ResponseEntity<Student> create(@RequestBody Student student) {
        try {
            Student _student = studentRepository.save(student);
            return new ResponseEntity<>(_student, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/course/{name}/student/{id}")
    ResponseEntity<Optional<Student>> registerCourse(@PathVariable("name") String courseName,@PathVariable("id") Long id) {

        Optional<Course> course = Optional.ofNullable(courseRepository.findByCourseName(courseName)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "course is not found")));

        Optional<Student> student = Optional.ofNullable(studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "student is not found")));

        if (course.isPresent()) {
            String teacherName = course.get().getTeacher().getTeacherName();
            String url = "http://localhost:8090/teachers/" + teacherName + "/courses/" + courseName + "/students/" + id;
            ResponseEntity<Teacher> responseEntity = restTemplate.postForEntity(url, null, Teacher.class);
            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
