package coms309.search;

import java.util.List;
import java.util.Set;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.util.MultiValueMap;

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

    @ApiOperation(value = "Search for users by similar values", response = List.class, tags = "searchUserFirstLast")
    @GetMapping("/user/fuzzy")
    private List<Integer> searchFuzzy(@RequestParam(defaultValue = "-1") int myId, @RequestParam(required = false) Map<String, String> params) {
        return searchService.searchUser(myId, params, false);
    }

    @ApiOperation(value = "Search for users by their exact values", response = List.class, tags = "searchUserFirstLast")
    @GetMapping("/user/exact")
    private List<Integer> searchExact(@RequestParam(defaultValue = "-1") int myId, @RequestParam(required = false) Map<String, String> params) {
        return searchService.searchUser(myId, params, true);
    }

    @ApiOperation(value = "Search for users that share one or more similar values", response = List.class, tags = "searchUserFirstLast")
    @GetMapping("/user/fuzzyOr")
    private Set<Integer> searchFuzzyOr(@RequestParam(defaultValue = "-1") int myId, @RequestParam(required = false) Map<String, String> params) {
        return searchService.searchUserOr(myId, params, false);
    }

    @ApiOperation(value = "Search for users that share one or more exact values", response = List.class, tags = "searchUserFirstLast")
    @GetMapping("/user/exactOr")
    private Set<Integer> searchExactOr(@RequestParam(defaultValue = "-1") int myId, @RequestParam(required = false) Map<String, String> params) {
        return searchService.searchUserOr(myId, params, true);
    }
}
