package coms309.serverpeople;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This allows serverpeople to be found in the repository by a personId or a serverId
 *
 * @author Tyler Green
 */
@Repository
public interface ServerPeopleRepository extends JpaRepository<ServerPeople, Integer> {
    // There are a lot of pre implemented methods Some of the examples are as below
    // Person save(Person person)
    // List<Person> findAll()
    // Person findById(int id)
    // saveAll(List<Person> people)
    List<ServerPeople> findByServerId(int serverId);
    List<ServerPeople> findByPersonId(int personId);
}
