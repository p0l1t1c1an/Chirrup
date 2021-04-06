package coms309.directmessage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectMessageService {
    @Autowired
    DirectMessageRepository directMessageRepository;
    public List<DirectMessage> getAllDirectMessages() {
        List<DirectMessage> dms = new ArrayList<DirectMessage>();
        directMessageRepository.findAll().forEach(dm -> dms.add(dm));
        return dms;
    }

    public DirectMessage getDirectMessageById(int id) {  
        return directMessageRepository.findById(id).get();  
    }

    public void saveOrUpdate(DirectMessage dm) {  
        directMessageRepository.save(dm);  
    }

    //deleting a specific record  
    public void delete(int id) {  
        directMessageRepository.deleteById(id);  
    }
}
