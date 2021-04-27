package coms309.searchTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;

import coms309.user.User;
import coms309.user.UserService;

import coms309.search.SearchService;

import coms309.ServerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, 
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchServiceTest { 

    @InjectMocks
	SearchService searchService;
    
    @Mock
    UserService userService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

    @Test
    public void usernameSearchTest() {
        List<User> userList = new ArrayList<User>();
        userList.add(Mockito.spy(User.class));
        userList.add(Mockito.spy(User.class));
        userList.add(Mockito.spy(User.class));
   
        userList.get(0).setUsername("Jacob");
        userList.get(0).setId(1);
        
        userList.get(1).setUsername("Tyler");
        userList.get(1).setId(2);
        
        userList.get(2).setUsername("Jeremy");
        userList.get(2).setId(3);

        Map<String, String> map = new HashMap<String, String>();
        map.put("user", "Jacob");

		when(userService.getAllUser()).thenReturn(new ArrayList<User>(userList));

        List<Integer> search = searchService.searchUser(-1, map, true);

        assertEquals(1, search.size());

        // Order for search should just be the first user in list
        assertEquals(Integer.valueOf(1), search.get(0));
    }

    @Test
    public void fuzzySearchTest() {
        List<User> userList = new ArrayList<User>();
        userList.add(Mockito.spy(User.class));
        userList.add(Mockito.spy(User.class));
   
        userList.get(0).setUsername("Test_User");
        userList.get(0).setId(1);
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("user", "Test_Us");
  
    	when(userService.getAllUser()).thenReturn(new ArrayList<User>(userList));
  
        List<Integer> search = searchService.searchUser(-1, map, false);
  
        assertEquals(1, search.size());
  
        // Order for search should just be the first user in list
        assertEquals(Integer.valueOf(1), search.get(0));
    }


    @Test
    public void firstSearchTest() {
        List<User> userList = new ArrayList<User>();
        userList.add(Mockito.spy(User.class));
        userList.add(Mockito.spy(User.class));
        userList.add(Mockito.spy(User.class));
   
        userList.get(0).setFirstname("Jacob");
        userList.get(0).setId(1);
        
        userList.get(1).setFirstname("Tyler");
        userList.get(1).setId(2);
        
        userList.get(2).setFirstname("Jeremy");
        userList.get(2).setId(3);

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", "Tyler");

		when(userService.getAllUser()).thenReturn(new ArrayList<User>(userList));

        List<Integer> search = searchService.searchUser(-1, map, true);

        assertEquals(1, search.size());

        // Order for search should just be the second user in list
        assertEquals(Integer.valueOf(2), search.get(0));
    }

    @Test
    public void lastSearchTest() {
        List<User> userList = new ArrayList<User>();
        userList.add(Mockito.spy(User.class));
        userList.add(Mockito.spy(User.class));
        userList.add(Mockito.spy(User.class));
   
        userList.get(0).setLastname("Boicken");
        userList.get(0).setId(1);
        
        userList.get(1).setLastname("Green");
        userList.get(1).setId(2);
        
        userList.get(2).setLastname("Noeson");
        userList.get(2).setId(3);

        Map<String, String> map = new HashMap<String, String>();
        map.put("last", "Noeson");

		when(userService.getAllUser()).thenReturn(new ArrayList<User>(userList));

        List<Integer> search = searchService.searchUser(-1, map, true);

        assertEquals(1, search.size());

        // Order for search should just be the third user in list
        assertEquals(Integer.valueOf(3), search.get(0));
    }
    
    @Test
    public void orSearchTest() {
        List<User> userList = new ArrayList<User>();
        userList.add(Mockito.spy(User.class));
        userList.add(Mockito.spy(User.class));
        userList.add(Mockito.spy(User.class));
   
        userList.get(0).setUsername("Jacob");
        userList.get(0).setFirstname("Jacob");
        userList.get(0).setLastname("Boicken");
        userList.get(0).setId(1);
        
        userList.get(1).setUsername("Tyler");
        userList.get(1).setFirstname("Tyler");
        userList.get(1).setLastname("Green");
        userList.get(1).setId(2);
        
        userList.get(2).setUsername("Jeremy");
        userList.get(2).setFirstname("Jeremy");
        userList.get(2).setLastname("Noeson");
        userList.get(2).setId(3);
            
        Map<String, String> map = new HashMap<String, String>();
        map.put("user", "Jacob");
        map.put("first", "Tyler");
        map.put("last", "Noeson");

		when(userService.getAllUser()).thenReturn(new ArrayList<User>(userList));

        List<Integer> search = new ArrayList<Integer>(searchService.searchUserOr(-1, map, true));

        assertEquals(3, search.size());

        // Should be all the users in list
        assertEquals(Integer.valueOf(1), search.get(0));
        assertEquals(Integer.valueOf(2), search.get(1));
        assertEquals(Integer.valueOf(3), search.get(2));
    }


}
