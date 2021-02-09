package tjgreen.iastate.edu.exp3.dao;

import java.util.List;

import tjgreen.iastate.edu.exp3.model.Person;

public interface PersonDao {
    int insertPerson(Person person);
    List<Person> getPersons();
    
    default int addPerson(Person person) {
        return insertPerson(person);
    }

    default List<Person> listPersons() {
        return getPersons();
    }
}
