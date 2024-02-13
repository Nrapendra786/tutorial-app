package org.example;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping(value = "/tutorials")
@Transactional
@Slf4j
public class TutorialController {

    private TutorialRepository repository;

    @Autowired
    TutorialController(TutorialRepository tutorialRepository){
        this.repository = tutorialRepository;
    }

    @GetMapping("/")
    List<Tutorial> all() {
        List<Tutorial> tutorials = repository.findAll();
        return tutorials;
    }

    @GetMapping("/{id}")
    Optional<Tutorial> one(@PathVariable Long id) {
        return Optional.of(repository.findById(id)
                .orElseThrow(() -> new TutorialException("tutorial is not found")));
    }

    @PutMapping("/{id}")
    ResponseEntity<Optional<Tutorial>> update(@PathVariable Long id, @RequestBody Tutorial tutorial) {
        try {
            Optional.ofNullable(repository.findById(id).orElseThrow(() -> new TutorialException("tutorial is not found")));
            repository.saveAndFlush(tutorial);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    ResponseEntity<Tutorial> create(@RequestBody Tutorial tutorial) {
        try {
            Tutorial _tutorial = repository.save(tutorial);
            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Tutorial> delete(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    ResponseEntity<Tutorial> deleteAll() {
        try {
            repository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/published/")
    ResponseEntity<List<Tutorial>> published(@PathVariable Long id) {
        try {
            List<Tutorial> tutorials = repository.findByPublished("Physics");
            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
