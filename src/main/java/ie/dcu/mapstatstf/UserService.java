package ie.dcu.mapstatstf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class UserService {
    // default constructor
    public UserService() {
    }

    public List<UserModel> listUsers()
    {
        try{
        // set the relative path of the location of the json file
        String fileLocation = "./src/main/resources/users.json";
        // create a jackson object mapper to use for converting an object to a json file
        ObjectMapper mapper = new ObjectMapper();
        // tell the mapper to parse a single value as an array to avoid errors
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        // check if the file already exists at the location specified
        File f = new File(fileLocation);
        if(f.isFile()) {
            // if the file does exist, use the mapper to read the value and parse it as an arraylist
            ArrayList<UserModel> userList = new ArrayList<UserModel>(Arrays.asList(mapper.readValue(f, UserModel[].class)));
            // add the new stat entry onto the list
            return userList;
        }

        } catch (Exception ex)
        {
            // print the stack trace in case there's an error here reading/writing to file
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    // saves/appends a user's info to a file
    public UserModel addUser(UserModel user)
    {
        // create a unique identifier for the entry to be saved
        try{
            // set the relative path of the location of the json file
            String fileLocation = "./src/main/resources/users.json";
            // create a jackson object mapper to use for converting an object to a json file
            ObjectMapper mapper = new ObjectMapper();
            // tell the mapper to parse a single value as an array to avoid errors
            mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

            // check if the file already exists at the location specified
            File f = new File(fileLocation);
            if(f.isFile()) {
                // if the file does exist, use the mapper to read the value and parse it as an arraylist
                ArrayList<UserModel> userList = new ArrayList<UserModel>(Arrays.asList(mapper.readValue(f, UserModel[].class)));
                // add the new stat entry onto the list
                userList.add(user);
                // overwrite the contents of the file with the newly extended list
                mapper.writeValue(f, userList);
            }
            else {
                // create a new file by writing the single entry
                mapper.writeValue(f, user);
            }

        } catch (Exception ex)
        {
            // print the stack trace in case there's an error here reading/writing to file
            ex.printStackTrace();
        }
        return user;
    }
}
