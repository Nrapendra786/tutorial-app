package com.nrapendra.teacher;

import com.nrapendra.commons.exceptions.NoSuchElementException;
import com.nrapendra.student.Student;
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

    @PostMapping("/{teacherName}/course/{courseName}/student/{id}")
    ResponseEntity<Teacher> accept(@PathVariable("teacherName") String teacherName,
                                   @PathVariable("courseName") String courseName,
                                   @PathVariable("student") Long id) {

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
