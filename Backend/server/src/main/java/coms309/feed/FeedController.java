package coms309.feed;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import coms309.post.Post;

//creating RestController  
@Api(value = "FeedController", description = "Rest APIs for generating a users feed of posts")
@RequestMapping("/api")
@RestController
public class FeedController {

    @Autowired
    FeedService feedService;

    @ApiOperation(value = "Generate a feed based by a user's id value", response = List.class, tags = "generateFeed")
    @GetMapping("/feed/{id}")
    private List<Integer> generateFeedById(@PathVariable("id") int id, @RequestParam(defaultValue = "false") boolean mostLiked) {
        return feedService.feedById(id, mostLiked);
    }

    @ApiOperation(value = "Generate a generic feed based", response = List.class, tags = "generateFeed")
    @GetMapping("/feed")
    private List<Integer> generateFeed(@RequestParam(defaultValue = "false") boolean mostLiked) {
        return feedService.feedAll(mostLiked);
    }

}
