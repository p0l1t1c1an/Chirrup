package coms309.roleTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;

import coms309.role.Role;
import coms309.role.RoleRepository;
import coms309.role.RoleService;

import coms309.ServerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, 
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoleServiceTest {
    @InjectMocks
	RoleService service;

	@Mock
	RoleRepository repo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getRoleByIdTest() {
		when(repo.getRoleById(1)).thenReturn(new Role(1, "test role"));
		Role r = service.getRoleById(1);

		assertEquals(1, r.getId().intValue());
        assertEquals("test role", r.getRole());
	}

    @Test
	public void getAllRoleTest() {
        List<Role> roles = new ArrayList<Role>();
        Role role1 = new Role(1, "role one");
        Role role2 = new Role(2, "role two");
        Role role3 = new Role(3, "role three");

        roles.add(role1);
        roles.add(role2);
        roles.add(role3);

		when(repo.findAll()).thenReturn(roles);
        List<Role> roleList = service.getAllRole();

        assertEquals(3, roleList.size());
		verify(repo, times(1)).findAll();
	}

	@Test
	public void saveOrUpdateRoleTest() {
        List<Role> roles = new ArrayList<Role>();
        Role role = new Role(1, "role");
        roles.add(role);

		when(repo.save(role)).thenReturn(role);
        when(repo.findAll()).thenReturn(roles);

        service.saveOrUpdate(role);
        List<Role> roleList = service.getAllRole();

		verify(repo, times(1)).save(role);
        assertEquals(1, roleList.size());
	}

    @Test
	public void deleteRoleTest() {
        List<Role> roles = new ArrayList<Role>();
		doNothing().when(repo).deleteRoleById(1);
        when(repo.findAll()).thenReturn(roles);

        service.delete(1);
        List<Role> roleList = service.getAllRole();

		verify(repo, times(1)).deleteRoleById(1);
        assertEquals(0, roleList.size());
	}
}
