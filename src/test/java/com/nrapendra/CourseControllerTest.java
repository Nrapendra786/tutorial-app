package com.nrapendra;

import com.nrapendra.course.Course;
import com.nrapendra.course.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import java.util.concurrent.atomic.AtomicLong;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TutorialApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@DirtiesContext
public class CourseControllerTest {

    private static final String COURSE_URL = "/courses/";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @LocalServerPort
    private int port;

    private final AtomicLong atomicLong = new AtomicLong(1L);

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testCreateTutorial() {
        //given
        Course tutorial = Course.builder()
                .id(atomicLong.get())
                .courseName("physics")
                .description("physics tutorial")
                .title("physics")
                .published("physics")
                .build();

        //when
        ResponseEntity<Course> postResponse = restTemplate.postForEntity(getRootUrl() + COURSE_URL, tutorial, Course.class);

        //then
        Assertions.assertNotNull(postResponse);
        Assertions.assertNotNull(postResponse.getBody());
        Assertions.assertEquals(postResponse.getBody().getDescription(), "physics tutorial");
        Assertions.assertEquals(postResponse.getBody().getTitle(), "physics");
        Assertions.assertEquals(postResponse.getBody().getPublished(), "physics");
        Assertions.assertEquals(postResponse.getBody().getCourseName(), "physics");
    }

    @Test
    public void testGetTutorialById() {
        long id = atomicLong.get();

        Course tutorial = Course.builder()
                .id(id)
                .courseName("physics")
                .description("physics tutorial").title("physics").published("physics").build();

        ResponseEntity<Course> tutorialResponse = restTemplate.postForEntity(getRootUrl() + COURSE_URL, tutorial, Course.class);

        Assertions.assertNotNull(tutorialResponse.getBody());

        Course getResponse = restTemplate.getForObject(getRootUrl() + COURSE_URL + tutorialResponse.getBody().getId(), Course.class);

        Assertions.assertEquals("physics tutorial", getResponse.getDescription());
        Assertions.assertEquals("physics", getResponse.getTitle());
        Assertions.assertEquals("physics", getResponse.getPublished());
        Assertions.assertEquals("physics", getResponse.getCourseName());
    }

    @Test
    public void testUpdateTutorial() {
        //given
        long id = atomicLong.get();
        saveToTutorial(id);
        Course tutorial = restTemplate.getForObject(getRootUrl() + COURSE_URL + id, Course.class);

        tutorial = Course.builder()
                .id(id)
                .courseName("chemistry")
                .description("chemistry tutorial").title("chemistry").published("chemistry").build();

        //when
        restTemplate.put(getRootUrl() + "/courses/" + id, tutorial);

        //then
        Course postResponse = restTemplate.getForObject(getRootUrl() + COURSE_URL + id, Course.class);
        Assertions.assertEquals(postResponse.getDescription(), "chemistry tutorial");
        Assertions.assertEquals(postResponse.getTitle(), "chemistry");
        Assertions.assertEquals(postResponse.getPublished(), "chemistry");
        Assertions.assertEquals(postResponse.getCourseName(), "chemistry");
    }

    @Test
    public void testDeleteTutorial() {
        //given
        long id = atomicLong.get();

        Course tutorial = restTemplate.getForObject(getRootUrl() + COURSE_URL + id, Course.class);
        Assertions.assertNotNull(tutorial);

        //when
        restTemplate.delete(getRootUrl() + COURSE_URL + id);

        try {
            restTemplate.getForObject(getRootUrl() + COURSE_URL + id, Course.class);
        } catch (final HttpClientErrorException e) {
            //then
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    private void saveToTutorial(Long id) {
        Course tutorial = Course.builder()
                .id(id)
                .courseName("physics")
                .description("physics tutorial").title("physics").published("physics").build();
        courseRepository.saveAndFlush(tutorial);
    }

}
