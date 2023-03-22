package ie.dcu.mapstatstf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String Hello()
    {
        return "hello world doofus";
    }
}
