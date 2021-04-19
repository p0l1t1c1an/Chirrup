package coms309.directmessagegroup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectMessageGroupService {
    @Autowired
    DirectMessageGroupRepository groupRepository;

    public List<DirectMessageGroup> getAllGroups() {
        List<DirectMessageGroup> groups = new ArrayList<DirectMessageGroup>();
        groupRepository.findAll().forEach(group -> groups.add(group));
        return groups;
    }

    public DirectMessageGroup getGroupById(int id) {
        return groupRepository.findById(id).isPresent() ? groupRepository.findById(id).get() : null;
    }

    public void saveOrUpdate(DirectMessageGroup group) {
        groupRepository.save(group);
    }

    public void delete(int id) {
        groupRepository.deleteById(id);
    }
}
