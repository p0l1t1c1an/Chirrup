package coms309.admin;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import coms309.post.Post;

//creating RestController  
@Api(value = "AdminController", description = "Rest APIs for generating a users admin of posts")
@RequestMapping("/api")
@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @ApiOperation(value = "Generate a feed based on mosted reported posts", response = List.class, tags = "generateAdminFeed")
    @GetMapping("/admin/feed")
    private List<Integer> generateAdminFeed() {
        return adminService.adminFeed();
    }
   
    @ApiOperation(value = "Solve a report by editing the post", response = void.class, tags = "solveReportEdit")
    @PatchMapping("/admin/solve/{postId}")
    private void solveReportEdit(@PathVariable("postId") int postId, @RequestBody Post newPost) {
        adminService.solveEditPost(postId, newPost);
    }

    @ApiOperation(value = "Solve a report by ignoring the post", response = void.class, tags = "solveReportIgnore")
    @PutMapping("/admin/solve/{postId}")
    private void solveReportIgnore(@PathVariable("postId") int postId) {
        adminService.solveIgnorePost(postId);
    }

    @ApiOperation(value = "Solve a report by deleting the post", response = void.class, tags = "solveReportDelete")
    @DeleteMapping("/admin/solve/{postId}")
    private void solveReportDelete(@PathVariable("postId") int postId) {
        adminService.solveDeletePost(postId);
    }
}
