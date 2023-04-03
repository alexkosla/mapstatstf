package ie.dcu.mapstatstf;

//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service)
    {
        this.service = service;
    }

    @GetMapping("/test")
    @CrossOrigin(origins ="*")
    public String Hello()
    {
        return "hello you are in the test controller";
    }

    @PostMapping("/submit-user")
    @CrossOrigin(origins ="*")
    public ResponseEntity<Void> submitUser(@RequestBody UserModel user)
    {
        service.addUser(user);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
    }
}
