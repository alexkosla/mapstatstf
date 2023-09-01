package ie.dcu.mapstatstf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// main application function
@SpringBootApplication
//@ComponentScan("ie.dcu.mapstatstf.Repository")
//@ComponentScan("ie.dcu.mapstatstf.Controller")
//@ComponentScan("ie.dcu.mapstatstf.Service")

public class MapstatstfApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapstatstfApplication.class, args);
	}

}
