package coms309.userTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

import org.springframework.test.context.junit4.SpringRunner;

import coms309.ServerApplication;
import coms309.user.User;
import coms309.user.UserRepository;
import coms309.user.UserService;

import org.springframework.boot.test.context.SpringBootTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, 
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {
    @InjectMocks
    UserService service;

    @Mock
    UserRepository repo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUserByIdTest() {
        when(repo.getUserById(1)).thenReturn(new User(1, "test@email.com", "password", "username", "firstname", "lastname", 1, "111111", null, null, "biography"));
        service.saveOrUpdate(new User(1, "test@email.com", "password", "username", "firstname", "lastname", 1, "111111", null, null, "biography"));
        User user = service.getUserById(1);

        assertEquals(1, user.getId().intValue());
        assertEquals("test@email.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("firstname", user.getFirstname());
        assertEquals("lastname", user.getLastname());
        assertEquals(1, user.getRole());
        assertEquals("111111", user.getTelephone());
        assertEquals("biography", user.getBiography());

        verify(repo, times(1)).getUserById(1);
    }

    @Test
    public void getAllUserTest() {
        List<User> users = new ArrayList<User>();
        users.add(new User(1, "test@email.com", "password", "username", "firstname", "lastname", 1, "111111", null, null, "biography"));
        users.add(new User(2, "test@email.com", "password", "username", "firstname", "lastname", 1, "111111", null, null, "biography"));
        users.add(new User(3, "test@email.com", "password", "username", "firstname", "lastname", 1, "111111", null, null, "biography"));
        users.forEach(user -> service.saveOrUpdate(user));

        when(repo.findAll()).thenReturn(users);

        List<User> userList = service.getAllUser();

        assertEquals(3, userList.size());
    }

    @Test
    public void updateUserTest() {
        User user = new User(1, "test@email.com", "password", "username", "firstname", "lastname", 1, "111111", null, null, "biography");
        
        service.saveOrUpdate(user);
        user.setEmail("newEmail@email.com");
        when(repo.getUserById(1)).thenReturn(user);
        service.saveOrUpdate(user);

        User got = service.getUserById(1);

        assertEquals("newEmail@email.com", got.getEmail());
    }

    @Test
    public void followUserTest() {
        User user1 = new User(1, "test@email.com", "password", "username", "firstname", "lastname", 1, "111111", null, null, "biography");
        User user2 = new User(2, "test@email.com", "password", "username", "firstname", "lastname", 1, "111111", null, null, "biography");

        service.saveOrUpdate(user1);
        service.saveOrUpdate(user2);

        user2.addFollowing(user1);
        service.saveOrUpdate(user1);

        when(repo.getUserById(1)).thenReturn(user1);

        User userReturned1 = service.getUserById(1);

        assertEquals(userReturned1, user2.getFollowing().toArray()[0]);
    }
}