package com.nrapendra;

import com.nrapendra.tutorial.Tutorial;
import com.nrapendra.tutorial.TutorialRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TutorialApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled
class TutorialControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TutorialRepository tutorialRepository;

    @LocalServerPort
    private int port;

    private final AtomicLong atomicLong = new AtomicLong(1L);

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    //@Disabled
    public void testCreateTutorial() {
        //given
        Tutorial tutorial = new Tutorial();
        tutorial.setId(atomicLong.get());
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
    //@Disabled
    public void testGetAllTutorials() {
        //given
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        //when
        ResponseEntity<Tutorial[]> postResponse = restTemplate.exchange(getRootUrl() + "/tutorials/all/",
                HttpMethod.GET, entity, Tutorial[].class);

        //then
        Tutorial [] tutorial = postResponse.getBody();
        assert tutorial != null;
        Assertions.assertEquals(tutorial[0].getDescription(),"physics tutorial");
        Assertions.assertEquals(tutorial[0].getTitle(),"physics");
        Assertions.assertEquals(tutorial[0].getPublished(),"physics");
    }

    @Test
    //@Disabled
    public void testGetTutorialById() {
        Tutorial getResponse =  restTemplate.getForObject(getRootUrl() + "/tutorials/" + atomicLong.get(), Tutorial.class);
        Assertions.assertEquals(getResponse.getDescription(),"physics tutorial");
        Assertions.assertEquals(getResponse.getTitle(),"physics");
        Assertions.assertEquals(getResponse.getPublished(),"physics");
    }

    @Test
    //@Disabled
    public void testUpdateTutorial() {

        //given
        long id= atomicLong.get();
        Tutorial tutorial = restTemplate.getForObject(getRootUrl() + "/tutorials/" + id, Tutorial.class);
        tutorial.setId(atomicLong.get());
        tutorial.setDescription("chemistry tutorial");
        tutorial.setTitle("chemistry");
        tutorial.setPublished("chemistry");

        //when
        restTemplate.put(getRootUrl() + "/tutorials/" + id, tutorial);

        //then
        Tutorial postResponse = restTemplate.getForObject(getRootUrl() + "/tutorials/" + id, Tutorial.class);
        Assertions.assertEquals(postResponse.getDescription(),"chemistry tutorial");
        Assertions.assertEquals(postResponse.getTitle(),"chemistry");
        Assertions.assertEquals(postResponse.getPublished(),"chemistry");
    }

    @Test
    //@Disabled
    public void testDeleteTutorial() {
        //given
        int id = 2;
        Tutorial tutorial = restTemplate.getForObject(getRootUrl() + "/tutorials/" + atomicLong.get(), Tutorial.class);
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
