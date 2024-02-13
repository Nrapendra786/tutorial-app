package org.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface TutorialRepository extends JpaRepository<Tutorial,Long> {

    List<Tutorial> findByPublished(String published);

    List<Tutorial> findByTitleContaining(String title);


}
