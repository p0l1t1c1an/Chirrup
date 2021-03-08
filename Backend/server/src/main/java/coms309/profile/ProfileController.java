package coms309.profile;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//creating RestController  
@RequestMapping("/api")
@RestController
public class ProfileController {
    @Autowired
    ProfileService profileService;

    //creating a get mapping to get all profiles in the db
    @GetMapping("/profile")
    private List<Profile> getAllProfile() {
        return profileService.getAllProfile();
    }

    //creating a get mapping to get a specific profile
    @GetMapping("/profile/{id}")
    private Profile getProfile(@PathVariable("id") int id) {
        return profileService.getProfileById(id);
    }

    //creating a delete mapping to delete a certain profile, most likely this won't be used unless a user deletes their entire account
    @DeleteMapping("/profile/{id}")
    private void deleteProfile(@PathVariable("id") int id) {
        profileService.delete(id);
    }

    //creating a post mapping for updating profile
    @PatchMapping("/profile/{id}")
    private int saveProfile(@PathVariable("id") int id, @RequestBody Profile profile) {
        Profile updatedProfile = profileService.getProfileById(id);
        updatedProfile.setBiography(profile.getBiography());
        profileService.saveOrUpdate(updatedProfile);
        return updatedProfile.getId();
    }
}