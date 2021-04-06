package coms309.search;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

    @ApiOperation(value = "Search for users by user, first, and last names", response = List.class, tags = "searchUserFirstLast")
    @GetMapping("/user/")
    private List<User> searchUserFirstLast(@RequestParam String user, @RequestParam String first, @RequestParam String last) {
        return searchService.searchUser(user, first, last);
    }
}
