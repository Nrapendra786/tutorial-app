package com.nrapendra.teacher;

import com.nrapendra.commons.exceptions.NoSuchElementException;
import com.nrapendra.course.Course;
import com.nrapendra.course.CourseRepository;
import com.nrapendra.student.Student;
import com.nrapendra.student.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/teachers")
@Slf4j
@AllArgsConstructor
public class TeacherController {

    private TeacherRepository teacherRepository;

    private CourseRepository courseRepository;

    private StudentRepository studentRepository;

    @GetMapping("/{id}")
    ResponseEntity<Teacher> findOne(@PathVariable("id") Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(HttpStatus.NOT_FOUND,
                        "Not found student with id = " + id));
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<Optional<Student>> update(@PathVariable("id") Long id, @RequestBody Teacher teacher) {
        try {
            Optional.ofNullable(teacherRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(HttpStatus.NOT_FOUND,"student is not found")));
            teacherRepository.saveAndFlush(teacher);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    ResponseEntity<Teacher> create(@RequestBody Teacher teacher) {
        try {
            Teacher _teacher = teacherRepository.save(teacher);
            return new ResponseEntity<>(_teacher, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{teacherName}/courses/{courseName}/students/{id}")
    ResponseEntity<Optional<Teacher>> accept(@PathVariable("teacherName") String teacherName,
                                   @PathVariable("courseName") String courseName,
                                   @PathVariable("id") Long id) {

        Optional<Teacher> teacher = Optional.ofNullable(teacherRepository.findByTeacherName(teacherName)
                .orElseThrow(() -> new NoSuchElementException(HttpStatus.NOT_FOUND, "teacher not found")));

        Optional<Course> courseOptional = Optional.ofNullable(courseRepository.findByCourseName(courseName)
                .orElseThrow(() -> new NoSuchElementException(HttpStatus.NOT_FOUND, "course not found")));

        Optional<Student> student = Optional.ofNullable(studentRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException(HttpStatus.NOT_FOUND, "student not found")));

        if(courseOptional.isPresent() && teacher.isPresent() && student.isPresent()){
            Course courseRef = courseOptional.get();
            courseRef.setTeacher(teacher.get());
            courseRef.setStudent(student.get());
            courseRepository.saveAndFlush(courseRef);
        }
        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }
}
