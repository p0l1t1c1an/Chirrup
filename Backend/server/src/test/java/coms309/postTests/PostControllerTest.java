package coms309.postTests;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;

import coms309.ServerApplication;
import coms309.post.Post;
import coms309.post.PostService;
import coms309.user.User;
import coms309.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, 
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {
    @LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

    @Autowired
    PostService service;

    @Autowired
    UserService uService;

	@Test
	public void savePostTest() throws Exception {
        User user = new User(0, "test@email.com", "password", "username", "firstname", "lastname", 1, "111111", null, null, "biography");
        uService.saveOrUpdate(user);
        Post p = new Post(0, user, null, "testing controller");
        service.saveOrUpdate(p);

		String post = this.restTemplate.getForObject("http://localhost:" + port + "/api/posts/" + p.getId(), String.class);
        JSONObject postObj = new JSONObject(post);

        assertEquals(p.getContent(), postObj.getString("content"));
	}
}
