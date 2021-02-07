package coms309.people;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This provides the interface for Create, Read, Update and Delete of the Person class(people table)
 *
 * @author Vivek Bengre, edited further by Tyler Green
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    // There are a lot of pre implemented methods Some of the examples are as below
    // Person save(Person person)
    // List<Person> findAll()
    // Person findById(int id)
    // saveAll(List<Person> people)
}
