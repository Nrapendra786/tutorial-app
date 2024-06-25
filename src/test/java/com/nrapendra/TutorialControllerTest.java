package com.nrapendra;

import com.nrapendra.tutorial.Tutorial;
import com.nrapendra.tutorial.TutorialException;
import com.nrapendra.tutorial.TutorialRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TutorialApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TutorialControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TutorialRepository tutorialRepository;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    @Order(1)
    public void testGetAllTutorials() {
        //given
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        //when
        ResponseEntity<Tutorial[]> postResponse = restTemplate.exchange(getRootUrl() + "/tutorials/all/",
                HttpMethod.GET, entity, Tutorial[].class);

        //then
        Tutorial [] tutorial = postResponse.getBody();
        Assertions.assertEquals(tutorial[0].getDescription(),"physics tutorial");
        Assertions.assertEquals(tutorial[0].getTitle(),"physics");
        Assertions.assertEquals(tutorial[0].getPublished(),"physics");
    }

    @Test
    @Order(2)
    public void testGetTutorialById() {
        tutorialRepository.deleteAll();
        TutorialException tutorialException = assertThrows(TutorialException.class,
                () -> restTemplate.getForObject(getRootUrl() + "/tutorials/1", Tutorial.class));
    }

    @Test
    @Order(3)
    public void testCreateTutorial() {
        //given
        Tutorial tutorial = new Tutorial();
        tutorial.setDescription("physics tutorial");
        tutorial.setTitle("physics");
        tutorial.setPublished("physics");

        //when
        ResponseEntity<Tutorial> postResponse = restTemplate.postForEntity(getRootUrl() + "/tutorials/", tutorial, Tutorial.class);

        //then
        Assertions.assertNotNull(postResponse);
        Assertions.assertNotNull(postResponse.getBody());
        Assertions.assertEquals(postResponse.getBody().getDescription(),"physics tutorial");
        Assertions.assertEquals(postResponse.getBody().getTitle(),"physics");
        Assertions.assertEquals(postResponse.getBody().getPublished(),"physics");
    }

    @Test
    @Order(4)
    public void testUpdateTutorial() {
        //given
        int id = 0;
        Tutorial tutorial = restTemplate.getForObject(getRootUrl() + "/tutorials/" + id, Tutorial.class);
        tutorial.setId(tutorial.getId());
        tutorial.setDescription("physics tutorial");
        tutorial.setTitle("physics");
        tutorial.setPublished("physics");

        //when
        restTemplate.put(getRootUrl() + "/tutorials/" + tutorial.getId(), tutorial);

        //then
        Tutorial postResponse = restTemplate.getForObject(getRootUrl() + "/tutorials/" + tutorial.getId(), Tutorial.class);
        Assertions.assertEquals(postResponse.getDescription(),"physics tutorial");
        Assertions.assertEquals(postResponse.getTitle(),"physics");
        Assertions.assertEquals(postResponse.getPublished(),"physics");
    }

    @Test
    @Order(5)
    public void testDeleteTutorial() {
        //given
        int id = 2;
        Tutorial tutorial = restTemplate.getForObject(getRootUrl() + "/tutorials/" + id, Tutorial.class);
        Assertions.assertNotNull(tutorial);

        //when
        restTemplate.delete(getRootUrl() + "/tutorials/" + id);

        try {
            restTemplate.getForObject(getRootUrl() + "/tutorials/" + id, Tutorial.class);
        } catch (final HttpClientErrorException e) {
            //then
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
