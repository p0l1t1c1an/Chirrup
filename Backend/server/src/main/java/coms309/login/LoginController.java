package coms309.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//creating RestController  
@Api(value = "LoginController", description = "Rest APIs for logging in as a user")
@RequestMapping("/api")
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @ApiOperation(value = "Login sending a username and password", response = String.class, tags = "login")
    @PostMapping("/login")
    private String login(@RequestParam String user, @RequestParam String pass) {
        return String.valueOf(loginService.login(user, pass));
    } 
}
