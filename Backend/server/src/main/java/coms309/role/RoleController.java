package coms309.role;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//creating RestController  
@Api(value = "RoleController", description = "REST APIs for role entity")
@RequestMapping("/api")
@RestController
public class RoleController {
    // autowired the StudentService class
    @Autowired
    RoleService roleService;
    Logger logger = LoggerFactory.getLogger(RoleController.class);    

    //creating a get mapping for returning all roles in the db
    @ApiOperation(value = "lists all roles", response = Iterable.class, tags = "getAllRole")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/role")
    private List<Role> getAllRole() {
        logger.info("got all roles");
        return roleService.getAllRole();
    }

    //creating a get mapping for returning a specific role
    @ApiOperation(value = "gets a role by id", response = Role.class, tags = "getRole")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/role/{id}")
    private Role getRole(@PathVariable("id") int id) {
        logger.info("got role by id");
        return roleService.getRoleById(id);
    }

    //creating a delete mapping for removing a role
    @ApiOperation(value = "deletes a role by id", response = String.class, tags = "deleteRole")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @DeleteMapping("/role/{id}")
    private String deleteRole(@PathVariable("id") int id) {
        logger.info("deleted role by id");
        roleService.delete(id);
        return "Role deleted: " + id;
    }

    //creating post mapping the creates a new role
    @ApiOperation(value = "creates a new role", response = String.class, tags = "saveRole")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @PostMapping("/role")
    private String saveRole(@RequestBody Role role) {
        logger.info("created a new role");
        roleService.saveOrUpdate(role);
        return "Role created: " + role.getRole();
    }

    //creating an update mapping that edits an existing role
    @ApiOperation(value = "updates a role by id", response = String.class, tags = "updateRole")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Suceess|OK"),
        @ApiResponse(code = 401, message = "not authorized!"), 
        @ApiResponse(code = 403, message = "forbidden!!!"),
        @ApiResponse(code = 404, message = "not found!!!") })
    @PatchMapping("/role/{id}")
    private String updateRole(@PathVariable("id") int id, @RequestBody Role role) {
        Role current = roleService.getRoleById(id);
        current.setRole(role.getRole());
        logger.info("updated role by id");
        roleService.saveOrUpdate(current);
        return "Role updated: " + current.getRole();
    }
}