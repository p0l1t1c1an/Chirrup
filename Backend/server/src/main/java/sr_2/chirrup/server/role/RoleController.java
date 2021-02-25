package sr_2.chirrup.server.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class RoleController {
    @Autowired
    RoleRepository roleDB;

    @RequestMapping(method = RequestMethod.POST, path = "/role/add")
    public String addPerson(@RequestBody Role role) {
        roleDB.insertRole(role);
        return "Role added: " + role.getRole();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/role")
    public List<Role> getAllRoles() {
        return roleDB.getRoles();
    }
}