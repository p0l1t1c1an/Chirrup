package coms309.profile;  
import java.util.ArrayList;  
import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  

@Service  
public class ProfileService {

@Autowired  
ProfileRepository profileRepository;  
    public List<Profile> getAllProfile() {  
        List<Profile> profiles = new ArrayList<Profile>();  
        profileRepository.findAll().forEach(profile -> profiles.add(profile));  
        return profiles;
    }

    //getting a specific record  
    public Profile getProfileById(int id) {  
        return profileRepository.findById(id).get();  
    }  

    public void saveOrUpdate(Profile profile) {  
        profileRepository.save(profile);  
    }

    //deleting a specific record  
    public void delete(int id) {  
        profileRepository.deleteById(id);  
    }
}