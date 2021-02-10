package tjgreen.iastate.edu.exp4.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tjgreen.iastate.edu.exp4.model.Person;
import tjgreen.iastate.edu.exp4.repository.PersonRepository;

@RequestMapping("/api")
@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @RequestMapping(method = RequestMethod.POST, path = "/persons/add")
    public String addPerson(@RequestBody Person person) {
        personRepository.save(person);
        return "Person added: " + person.getFirstName();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/persons")
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
}
