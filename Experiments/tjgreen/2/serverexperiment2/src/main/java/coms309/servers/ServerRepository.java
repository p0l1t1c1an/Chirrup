package coms309.servers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This provides the interface for Create, Read, Update and Delete of the server table
 *
 * @author Tyler Green
 */
@Repository
public interface ServerRepository extends JpaRepository<Server, Integer> {
    // There are a lot of pre implemented methods Some of the examples are as below
    // Person save(Person person)
    // List<Person> findAll()
    // Person findById(int id)
    // saveAll(List<Person> people)
}
