package coms309.role;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/role")
    private List<Role> getAllRole() {
        return roleService.getAllRole();
    }

    @GetMapping("/role/{id}")
    private Role getRole(@PathVariable("id") int id) {
        return roleService.getRoleById(id);
    }

    @DeleteMapping("/role/{id}")
    private void deleteRole(@PathVariable("id") int id) {
        roleService.delete(id);
    }

    // creating post mapping that post the student detail in the database
    @PostMapping("/role")
    private int saveRole(@RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return role.getId();
    }
}