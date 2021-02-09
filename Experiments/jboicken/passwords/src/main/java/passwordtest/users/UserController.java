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
 * @author Jacob Boicken, modified version Vivek Bengre's person class
 */

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    // gets a JSON request parses it into a person object and enters it into the database
    @RequestMapping(method = RequestMethod.POST, path = "/user/new")
    public String saveperson(User user) {
        userRepository.save(user);
        return "New user "+ user.getId() + " has been saved";
    }

    // gets all the people in the database and returns it in the form of a JSON
    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<User> getAllPerson() {        
        List<User> results = userRepository.findAll();
        return results;
    }

    // gets the person by the id specified in the request URL and returns it in JSON format
    @RequestMapping(method = RequestMethod.GET, path = "/person/{personId}")
    public Optional<User> findpersonById(@PathVariable("personId") int id) {
        Optional<User> results = userRepository.findById(id);
        return results;
    }
}
