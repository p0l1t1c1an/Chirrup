package tjgreen.iastate.edu.exp3.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import tjgreen.iastate.edu.exp3.dao.PersonDao;
import tjgreen.iastate.edu.exp3.model.Person;

@Repository
public class PersonRepository implements PersonDao {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(Person person) {
        DB.add(new Person(person.getFirstName(), person.getLastName()));
        return 0;
    }

    @Override
    public List<Person> getPersons() {
        return DB;
    }
    
}
