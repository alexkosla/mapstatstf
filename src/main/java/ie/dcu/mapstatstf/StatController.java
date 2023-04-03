package ie.dcu.mapstatstf;

//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatController {

    private final StatService service;

    public StatController(StatService service)
    {
        this.service = service;
    }

    @GetMapping("/test-stat")
    @CrossOrigin(origins ="*")
    public String HelloStat()
    {
        return "hello you are in the stat controller";
    }

    @PostMapping("/submit-stats")
    @CrossOrigin(origins ="*")
    public ResponseEntity<Void> submitUser(@RequestBody StatModel stat)
    {
        service.addStat(stat);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
    }
}
