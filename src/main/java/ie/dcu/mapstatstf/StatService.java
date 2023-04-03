package ie.dcu.mapstatstf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class StatService {
    // default constructor
    public StatService() {
    }

    // saves/appends a stat entry to a file
    public StatModel addStat(StatModel stat)
    {
        // create a unique identifier for the entry to be saved
        if(stat.getStatId() == null)
            stat.setStatId(UUID.randomUUID());

        try{
            // set the relative path of the location of the json file
            String fileLocation = "./src/main/resources/stats.json";
            // create a jackson object mapper to use for converting an object to a json file
            ObjectMapper mapper = new ObjectMapper();
            // tell the mapper to parse a single value as an array to avoid errors
            mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

            // check if the file already exists at the location specified
            File f = new File(fileLocation);
            if(f.isFile()) {
                // if the file does exist, use the mapper to read the value and parse it as an arraylist
                ArrayList<StatModel> statList = new ArrayList<StatModel>(Arrays.asList(mapper.readValue(f, StatModel[].class)));
                // add the new stat entry onto the list
                statList.add(stat);
                // overwrite the contents of the file with the newly extended list
                mapper.writeValue(f, statList);
            }
            else {
                // create a new file by writing the single entry
                mapper.writeValue(f, stat);
            }

        } catch (Exception ex)
        {
            // print the stack trace in case there's an error here reading/writing to file
            ex.printStackTrace();
        }
        return stat;
    }
}
