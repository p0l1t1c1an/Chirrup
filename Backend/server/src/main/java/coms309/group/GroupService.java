package coms309.group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepository;

    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<Group>();
        groupRepository.findAll().forEach(group -> groups.add(group));
        return groups;
    }

    public Group getGroupById(int id) {
        return groupRepository.findById(id).get();
    }

    public void saveOrUpdate(Group group) {
        groupRepository.save(group);
    }

    public void delete(int id) {
        groupRepository.deleteById(id);
    }
}
