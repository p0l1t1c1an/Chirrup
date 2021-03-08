package coms309.role;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//creating RestController  
@RequestMapping("/api")
@RestController
public class RoleController {
    // autowired the StudentService class
    @Autowired
    RoleService roleService;

    //creating a get mapping for returning all roles in the db
    @GetMapping("/role")
    private List<Role> getAllRole() {
        return roleService.getAllRole();
    }

    //creating a get mapping for returning a specific role
    @GetMapping("/role/{id}")
    private Role getRole(@PathVariable("id") int id) {
        return roleService.getRoleById(id);
    }

    //creating a delete mapping for removing a role
    @DeleteMapping("/role/{id}")
    private String deleteRole(@PathVariable("id") int id) {
        roleService.delete(id);
        return "Role deleted: " + id;
    }

    //creating post mapping the creates a new role
    @PostMapping("/role")
    private String saveRole(@RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return "Role created: " + role.getRole();
    }

    //creating an update mapping that edits an existing role
    @PatchMapping("/role/{id}")
    private String updateRole(@PathVariable("id") int id, @RequestBody Role role) {
        Role current = roleService.getRoleById(id);
        current.setRole(role.getRole());
        return "Role updated: " + current.getRole();
    }
}