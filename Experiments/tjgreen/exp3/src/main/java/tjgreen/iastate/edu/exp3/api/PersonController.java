package tjgreen.iastate.edu.exp3.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tjgreen.iastate.edu.exp3.model.Person;
import tjgreen.iastate.edu.exp3.service.PersonService;

@RequestMapping("")
@RestController
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public String addPerson(@RequestBody Person person) {
        personService.addPerson(person);
        return "Person added: " + person.getFirstName();
    }

    @GetMapping
    public List<Person> listPersons() {
        return personService.listPersons();
    }
}
