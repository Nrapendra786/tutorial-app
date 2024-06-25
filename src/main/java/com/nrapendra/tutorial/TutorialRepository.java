package com.nrapendra.tutorial;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface TutorialRepository extends JpaRepository<Tutorial,Long> {

    List<Tutorial> findByPublished(String published);
}
