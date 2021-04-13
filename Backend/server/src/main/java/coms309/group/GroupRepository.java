package coms309.group;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Group, Integer> {
    // Group getGroupById(int id);
    // List<Group> getAllGroup();
    // void deleteGroupById(int id);
}