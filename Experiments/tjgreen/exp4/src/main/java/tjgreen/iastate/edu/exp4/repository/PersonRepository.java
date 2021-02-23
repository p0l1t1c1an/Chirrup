package tjgreen.iastate.edu.exp4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tjgreen.iastate.edu.exp4.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    // private static List<Person> DB = new ArrayList<>();

    // @Override
    // public int insertPerson(Person person) {
    //     DB.add(new Person(person.getFirstName(), person.getLastName()));
    //     return 0;
    // }

    // @Override
    // public List<Person> getPersons() {
    //     return DB;
    // }
    
}
