package coms309;

import coms309.user.User;
import coms309.user.UserRepository;
import coms309.settings.StandardSettings;
import coms309.settings.SettingsService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

    @Bean
    CommandLineRunner initUser(UserRepository userRepository, SettingsService settingsService) {
        return args -> {
            User user1 = new User(1, "jboicken@somemail.com", "passwd", "jboicken", "Jacob", "Boicken", 1, "309", "01/01/2001");
            StandardSettings settings1 = new StandardSettings(user1);

            userRepository.insertUser(user1);
            settingsService.saveStandard(settings1);
            
        };
    }

}
