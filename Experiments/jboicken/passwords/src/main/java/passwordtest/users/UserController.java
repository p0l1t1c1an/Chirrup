package passwordtest.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Controller used to showcase Create and Read from database
 *
 * @author Jacob Boicken, modified version Vivek Bengre's user class
 */

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    // gets a JSON request parses it into a user object and enters it into the database
    @RequestMapping(method = RequestMethod.POST, path = "/user/new")
    public String saveuser(User user) {
        userRepository.save(user);
        return "New user "+ user.getId() + " has been saved";
    }


   // function just to create dummy data, creates 4 random values and enters it into the database
    @RequestMapping(method = RequestMethod.GET, path = "/user/create")
    public String createDummyData() throws Exception {
        User o1 = new User(1, "JohnDoe", "SecretPass");
        User o2 = new User(2, "JaneDoe", "123456789");
        User o3 = new User(3, "SomePleb", "Cs309Spring21");
        User o4 = new User(4, "ChadChampion", "password");
        userRepository.save(o1);
        userRepository.save(o2);
        userRepository.save(o3);
        userRepository.save(o4);
        return "Successfully created dummy data";
    }


    // gets all the user in the database and returns it in the form of a JSON
    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<User> getAllUser() {        
        List<User> results = userRepository.findAll();
        return results;
    }

    // gets the user by the id specified in the request URL and returns it in JSON format
    @RequestMapping(method = RequestMethod.GET, path = "/user/{userId}")
    public Optional<User> finduserById(@PathVariable("userId") int id) {
        Optional<User> results = userRepository.findById(id);
        return results;
    }
}
