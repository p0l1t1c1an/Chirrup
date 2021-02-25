package user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class UserController {
    @Autowired
    List<User> userDB;

    @RequestMapping(method = RequestMethod.POST, path = "/user/add")
    public String addPerson(@RequestBody User user) {
        userDB.add(user);
        return "User added: " + user.getFirstname();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user")
    public List<User> getAllRoles() {
        return userDB;
    }
}