package coms309.login;

import coms309.user.User;
import coms309.user.UserService;

import java.util.List;

import org.hibernate.query.criteria.internal.path.SetAttributeJoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;  

@Service  
public class LoginService {
    
    @Autowired
    UserService userService;
    
    public int login(String username, String pass) {
        List<User> users = userService.getAllUser();
        users.removeIf(u -> !u.getUsername().equals(username)); 
        for(User u : users) {
            if(u.getPassword().equals(pass)) {
                return u.getId();
            }
        }
        return -1;
    }

}
