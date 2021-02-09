package tjgreen.iastate.edu.exp3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tjgreen.iastate.edu.exp3.dao.PersonDao;
import tjgreen.iastate.edu.exp3.model.Person;

@Service
public class PersonService {

    private final PersonDao personDao;
    
    @Autowired
    public PersonService(@Qualifier("personRepository") PersonDao personDao) {
        this.personDao = personDao;
    }

    public int addPerson(Person person) {
        return personDao.insertPerson(person);
    }

    public List<Person> listPersons() {
        return personDao.getPersons();
    }
}
