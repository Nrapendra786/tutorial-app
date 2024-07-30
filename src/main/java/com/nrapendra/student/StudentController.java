package com.nrapendra.student;


import com.nrapendra.commons.NoSuchElementException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/students")
@Slf4j
@AllArgsConstructor
public class StudentController {

    private StudentRepository studentRepository;

    @GetMapping("/all/")
    ResponseEntity<List<Student>> findAll() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Student> findOne(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(HttpStatus.NOT_FOUND,
                        "Not found student with id = " + id));
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<Optional<Student>> update(@PathVariable Long id, @RequestBody Student student) {
        try {
            Optional.ofNullable(studentRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(HttpStatus.NOT_FOUND,"student is not found")));
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
}
