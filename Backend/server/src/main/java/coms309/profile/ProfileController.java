package coms309.profile;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/profile")
    private List<Profile> getAllProfile() {
        return profileService.getAllProfile();
    }

    @GetMapping("/profile/{id}")
    private Profile getProfile(@PathVariable("id") int id) {
        return profileService.getProfileById(id);
    }

    @DeleteMapping("/profile/{id}")
    private void deleteProfile(@PathVariable("id") int id) {
        profileService.delete(id);
    }

    @PostMapping("/profile/{id}")
    private int saveProfile(@PathVariable("id") int id, @RequestBody Profile profile) {
        Profile updatedProfile = profileService.getProfileById(id);
        updatedProfile.setBiography(profile.getBiography());
        profileService.saveOrUpdate(updatedProfile);
        return updatedProfile.getId();
    }
}