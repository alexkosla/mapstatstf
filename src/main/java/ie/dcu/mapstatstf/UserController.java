package ie.dcu.mapstatstf;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// define the class as a controller for a REST api
@RestController
public class UserController {

    // create and define an instance of userService, for business logic
    private final UserService service;

    public UserController(UserService service)
    {
        this.service = service;
    }

    // test endpoint to see if my back-end is working
    @GetMapping("/test")
    @CrossOrigin(origins ="*")
    public String Hello()
    {
        return "hello you are in the test controller";
    }

    // POST endpoint for submitting a user
    @PostMapping("/submit-user")
    @CrossOrigin(origins ="*")
    public ResponseEntity<Void> submitUser(@RequestBody UserModel user)
    {
        // put business logic in the service, out of the controller
        service.addUser(user);
        // return a success code
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
    }

    // GET endpoint for returning list of all users
    @GetMapping("/users")
    @CrossOrigin(origins ="*")
    public ResponseEntity<List<UserModel>> getUsers()
    {
        System.out.println("YOOOO WE IN GET ALL USERS");
        // put business logic in the service, out of the controller
        // returns a list of all users saved with an ok message
        return ResponseEntity.ok(service.listUsers());
    }
}
