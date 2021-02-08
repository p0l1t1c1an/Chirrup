package passwordtest.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This provides the interface for Create, Read, Update and Delete of the Person class(people table)
 *
 * @author Vivek Bengre, change for User Class by Jacob Boicken
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // There are a lot of pre implemented methods Some of the examples are as below
    // Person save(Person person)
    // List<Person> findAll()
    // Person findById(int id)
    // saveAll(List<Person> people)
}
