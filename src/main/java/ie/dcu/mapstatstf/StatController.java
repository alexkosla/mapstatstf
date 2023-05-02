package ie.dcu.mapstatstf;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StatController {

    // create an instance of the StatService, where business logic is performed
    private final StatService service;

    public StatController(StatService service)
    {
        this.service = service;
    }

    // safe test endpoint to see if my back-end is working
    @GetMapping("/test-stat")
    @CrossOrigin(origins ="*")
    public String HelloStat()
    {
        return "hello you are in the stat controller";
    }

    // GET endpoint for returning list of all users
    @GetMapping("/stats")
    @CrossOrigin(origins ="*")
    public ResponseEntity<List<StatEntity>> getList()
    {
        // put business logic in the service, out of the controller
        // returns a list of all stats saved with an ok message
        return ResponseEntity.ok(service.listStats());
    }

    // GET endpoint for returning list of all users
    @GetMapping("/stats-ID")
    @CrossOrigin(origins ="*")
    public ResponseEntity<List<UserStatEntity>> getStatsById(@RequestParam long steam64Id)
    {
        // put business logic in the service, out of the controller
        // returns a list of all stats saved with an ok message
        return ResponseEntity.ok(service.listUserStats(steam64Id));
    }

    // POST endpoint to submit a new stat entry
    @PostMapping("/submit-stats")
    @CrossOrigin(origins ="*")
    public ResponseEntity<Void> submitStat(@RequestBody StatEntity stat)
    {
        // keep business logic out of the controller, use the service for that
        service.addStat(stat);
        // return success code
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
    }
}
