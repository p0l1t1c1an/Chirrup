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
            User user1 = new User("John", "john@somemail.com");
            
            user1.setLaptop(laptop1);
            user2.setLaptop(laptop2);
            user3.setLaptop(laptop3);            
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

        };
    }

}
