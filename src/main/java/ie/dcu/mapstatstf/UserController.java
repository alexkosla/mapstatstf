package ie.dcu.mapstatstf;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
