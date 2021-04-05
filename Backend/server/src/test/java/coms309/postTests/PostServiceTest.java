package coms309.postTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import coms309.post.Post;
import coms309.post.PostRepository;
import coms309.post.PostService;
import coms309.role.Role;
import coms309.settings.Settings;
import coms309.settings.UserSettings;
import coms309.user.User;
import net.bytebuddy.asm.Advice.Local;

public class PostServiceTest {

	@InjectMocks
	PostService postService;

	@Mock
	PostRepository postRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAccountByIdTest() {
		//Role testRole = new Role("test");
		//Settings testsettings = new UserSettings();
		User testUser = new User();
		testUser.setFirstname("john");
		testUser.setId(1);
		when(postService.getPostById(1)).thenReturn(new Post(1, testUser , LocalDateTime.now(), "new post"));

		Post p = postService.getPostById(1);

		assertEquals(1, p.getCreatorId());
		assertEquals("new post", p.getContent());
		assertEquals(testUser.getBiography(), p.getCreator().getBiography());
	}

	@Test
	public void getAllAccountTest() {
		List<Account> list = new ArrayList<Account>();
		Account acctOne = new Account(1, "John", "1234", "john@gmail.com");
		Account acctTwo = new Account(2, "Alex", "abcd", "alex@yahoo.com");
		Account acctThree = new Account(3, "Steve", "efgh", "steve@gmail.com");

		list.add(acctOne);
		list.add(acctTwo);
		list.add(acctThree);

		when(repo.getAccountList()).thenReturn(list);

		List<Account> acctList = acctService.getAccountList();

		assertEquals(3, acctList.size());
		verify(repo, times(1)).getAccountList();
	}
}
