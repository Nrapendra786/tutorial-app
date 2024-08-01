package com.nrapendra.course;

import com.nrapendra.commons.exceptions.NoSuchElementException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/courses")
@Slf4j
@AllArgsConstructor
public class CourseController {

    private CourseRepository courseRepository;

    @GetMapping("/{id}")
    ResponseEntity<Course> findOne(@PathVariable("id") Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(HttpStatus.NOT_FOUND,
                                    "Not found course with id = " + id));
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<Optional<Course>> update(@PathVariable("id") Long id, @RequestBody Course course) {
        try {
            Optional.ofNullable(courseRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(HttpStatus.NOT_FOUND,"course is not found")));
            courseRepository.saveAndFlush(course);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    ResponseEntity<Course> create(@RequestBody Course course) {
        try {
            Course _course = courseRepository.save(course);
            return new ResponseEntity<>(_course, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Course> delete(@PathVariable("id") Long id) {
        try {
            courseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/published/")
    ResponseEntity<List<Course>> published(@PathVariable("id") Long id) {
        try {
            List<Course> courses = courseRepository.findByPublished("Physics");
            if (courses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
