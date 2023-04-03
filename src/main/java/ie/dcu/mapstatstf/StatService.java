package ie.dcu.mapstatstf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.asm.TypeReference;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class StatService {
    private Map<UUID, StatModel> stats = new HashMap<>();
    public StatService() {
    }

    public StatModel addStat(StatModel stat)
    {
        // create a unique identifier for the entry to be saved
        if(stat.getStatId() == null)
        {
            stat.setStatId(UUID.randomUUID());
        }

        try{
            // create a jackson object mapper to use for converting an object to a json file
            String fileLocation = "./src/main/resources/stats.json";
            Path path = Paths.get(fileLocation);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

//            Map<UUID, StatModel> statMap = mapper.readValue(Paths.get("stats.json").toFile(), Map.class);
//            statMap.put(stat.getStatId(), stat);
//            mapper.writeValue(Paths.get("stats.json").toFile(), statMap);

            File f = new File(fileLocation);
            if(f.isFile()) {
                ArrayList<StatModel> statList = new ArrayList<StatModel>(Arrays.asList(mapper.readValue(f, StatModel[].class)));
                statList.add(stat);
                mapper.writeValue(f, statList);
            }
            else
            {
                mapper.writeValue(f, stat);
            }

            // read in the json file as a list of StatModel
//            List<StatModel> statList = Arrays.asList(mapper.readValue(Paths.get("stats.json").toFile(), StatModel[].class));
//            statList.add(stat);
//            mapper.writeValue(Paths.get("stats.json").toFile(), stat);

        } catch (Exception ex)
        {
            System.out.println("unable to stringify user '" + stat.getSteam64Id() + "' to .json file");
            ex.printStackTrace();
        }
        return stat;
    }
}
