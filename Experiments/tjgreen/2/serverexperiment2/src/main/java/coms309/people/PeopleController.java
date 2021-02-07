package coms309.people;

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
 * @author Vivek Bengre, edited further by Tyler Green
 */

@RestController
public class PeopleController {

    @Autowired
    PeopleRepository peopleRepository;

    // gets a JSON request parses it into a person object and enters it into the database
    @RequestMapping(method = RequestMethod.POST, path = "/person/new")
    public String saveperson(Person person) {
        peopleRepository.save(person);
        return "New person "+ person.getFirstName() + " Saved";
    }
     // function just to create dummy data, creates 4 random values and enters it into the database
    @RequestMapping(method = RequestMethod.GET, path = "/person/create")
    public String createDummyData() {
        Person o1 = new Person(1, "John", "Doe", "404 Not found", "some numbers");
        Person o2 = new Person(2, "Jane", "Doe", "Its a secret", "you wish");
        Person o3 = new Person(3, "Some", "Pleb", "Right next to the Library", "515-345-41213");
        Person o4 = new Person(4, "Chad", "Champion", "Reddit memes corner", "420-420-4200");
        peopleRepository.save(o1);
        peopleRepository.save(o2);
        peopleRepository.save(o3);
        peopleRepository.save(o4);
        return "Successfully created dummy data";
    }

    // gets all the people in the database and returns it in the form of a JSON
    @RequestMapping(method = RequestMethod.GET, path = "/people")
    public List<Person> getAllPerson() {
        
        List<Person> results = peopleRepository.findAll();
        return results;
    }

    // gets the person by the id specified in the request URL and returns it in JSON format
    @RequestMapping(method = RequestMethod.GET, path = "/person/{personId}")
    public Optional<Person> findpersonById(@PathVariable("personId") int id) {
        
        Optional<Person> results = peopleRepository.findById(id);
        return results;
    }
}
