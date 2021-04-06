package coms309.search;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import coms309.user.User;

//creating RestController  
@Api(value = "SearchController", description = "Rest APIs for searching entities the database")
@RequestMapping("/api/search")
@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

    // This many mapping method is temporary and should be replaced by a simple system that parses
    // the input search url and fills out many different tags (more scalable)

    @ApiOperation(value = "Search for users by username", response = List.class, tags = "searchUser")
    @GetMapping("/user/username={user}")
    private List<User> searchUser(@PathVariable("user") String username) {
        return searchService.searchUser(username, "", "");
    }

    @ApiOperation(value = "Search for users by firstname", response = List.class, tags = "searchFirst")
    @GetMapping("/user/firstname={first}")
    private List<User> searchFirst(@PathVariable("first") String firstname) {
        return searchService.searchUser("", firstname, "");
    }

    @ApiOperation(value = "Search for users by lastname", response = List.class, tags = "searchLast")
    @GetMapping("/user/lastname={last}")
    private List<User> searchLast(@PathVariable("last") String lastname) {
        return searchService.searchUser("", "", lastname);
    }

    @ApiOperation(value = "Search for users by username and firstname", response = List.class, tags = "searchUserFirst")
    @GetMapping("/user/username={user}+firstname={first}")
    private List<User> searchUserFirst(@PathVariable("user") String username, @PathVariable("first") String firstname) {
        return searchService.searchUser(username, firstname, "");
    }

    @ApiOperation(value = "Search for users by username and lastname", response = List.class, tags = "searchUserLast")
    @GetMapping("/user/username={user}+lastname={last}")
    private List<User> searchUserLast(@PathVariable("user") String username, @PathVariable("last") String lastname) {
        return searchService.searchUser(username, "", lastname);
    }

    @ApiOperation(value = "Search for users by firstname and lastname", response = List.class, tags = "searchFirstLast")
    @GetMapping("/user/firstname={first}+lastname={last}")
    private List<User> searchFirstLast(@PathVariable("first") String firstname, @PathVariable("last") String lastname) {
        return searchService.searchUser("", firstname, lastname);
    }

    @ApiOperation(value = "Search for users by user, first, and last names", response = List.class, tags = "searchUserFirstLast")
    @GetMapping("/user/username={user}+firstname={first}i+lastname={last}")
    private List<User> searchUserFirstLast(@PathVariable("user") String username, @PathVariable("first") String firstname, @PathVariable("last") String lastname) {
        return searchService.searchUser(username, firstname, lastname);
    }
}
