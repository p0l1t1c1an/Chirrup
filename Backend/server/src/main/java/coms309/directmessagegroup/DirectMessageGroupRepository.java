package coms309.directmessagegroup;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectMessageGroupRepository extends CrudRepository<DirectMessageGroup, Integer> {
    // DirectMessageGroup getDirectMessageGroupByGroupId(int id);
    // List<DirectMessageGroup> getAllDirectMessageGroup();
    // void deleteDirectMessageGroupByGroupId(int id);
}