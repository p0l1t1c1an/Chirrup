package coms309.system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController
class WelcomeController {
    @GetMapping
    String welcome() {
        return "Welcome to the chirrup api";
    }
}