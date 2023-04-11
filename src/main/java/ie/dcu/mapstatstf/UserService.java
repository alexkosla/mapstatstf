package ie.dcu.mapstatstf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.io.File;
import java.util.*;

@Service
public class UserService {
    // default constructor
    public UserService() {
    }

    // function returns a list of all users saved
    public List<UserModel> listUsers()
    {
        try (Connection conn = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/",
                        "root", "admin")) {

            boolean isValid = conn.isValid(0);
            System.out.println("Can I connect to database ?  : " + isValid);

            PreparedStatement selectStatement = conn.prepareStatement("select * from mydb.users");
//            selectStatement.setString(1, "Ali");
            ResultSet rs = selectStatement.executeQuery();
            ArrayList<UserModel> userList = new ArrayList<UserModel>();
            while (rs.next()) {
                long steam64Id = rs.getLong("Steam64Id");
                String steam3Id = rs.getString("Steam3Id");
                String username = rs.getString("Username");
                String preferredClass = rs.getString("PreferredClass");
                boolean isAdmin = rs.getBoolean("IsAdmin");

                userList.add(new UserModel(steam64Id, steam3Id, username, preferredClass, isAdmin));
            }
            return userList;

//            // set the relative path of the location of the json file
//            String fileLocation = "./src/main/resources/users.json";
//            // create a jackson object mapper to use for converting an object to a json file
//            ObjectMapper mapper = new ObjectMapper();
//            // tell the mapper to parse a single value as an array to avoid errors
//            mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
//
//            // check if the file already exists at the location specified
//            File f = new File(fileLocation);
//            if(f.isFile()) {
//                // if the file does exist, use the mapper to read the value and parse it as an arraylist
//                ArrayList<UserModel> userList = new ArrayList<UserModel>(Arrays.asList(mapper.readValue(f, UserModel[].class)));
//                // add the new stat entry onto the list
//                return userList;
//            }
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
        try (Connection conn = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/",
                        "root", "admin")){
            // set the relative path of the location of the json file


            boolean isValid = conn.isValid(0);
            System.out.println("Can I connect to database ?  : " + isValid);

            PreparedStatement selectUsersById = conn.prepareStatement("select * from mydb.users where steam64Id = ?");
            selectUsersById.setString(1, String.valueOf(user.getSteam64Id()));
            ResultSet rs = selectUsersById.executeQuery();
            System.out.println(rs.toString());

            if(rs.next())
            {
                // case where user already exists
                return user;
            }
            else
            {
                PreparedStatement insertNewUser = conn.prepareStatement("INSERT INTO `mydb`.`Users` (`Steam64ID`, `Steam3ID`, `Username`, `PreferredClass`, `IsAdmin`) VALUES (?, ?, ?, ?, ?);");
                insertNewUser.setLong(1, user.getSteam64Id());
                insertNewUser.setString(2, user.getSteam3Id());
                insertNewUser.setString(3, user.getUsername());
                insertNewUser.setString(4, user.getPreferredClass());
                insertNewUser.setBoolean(5, user.isAdmin());
                insertNewUser.executeUpdate();
                return user;
            }

//            ArrayList<UserModel> userList = new ArrayList<UserModel>();
//            while (rs.next()) {
//                long steam64Id = rs.getLong("Steam64Id");
//                String steam3Id = rs.getString("Steam3Id");
//                String username = rs.getString("Username");
//                String preferredClass = rs.getString("PreferredClass");
//                boolean isAdmin = rs.getBoolean("IsAdmin");
//                userList.add(new UserModel(steam64Id, steam3Id, username, preferredClass, isAdmin));
//
//                System.out.println("user exists already");
//            }
//
//            String fileLocation = "./src/main/resources/users.json";
//            // create a jackson object mapper to use for converting an object to a json file
//            ObjectMapper mapper = new ObjectMapper();
//            // tell the mapper to parse a single value as an array to avoid errors
//            mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
//
//            // check if the file already exists at the location specified
//            File f = new File(fileLocation);
//            if(f.isFile()) {
//                // if the file does exist, use the mapper to read the value and parse it as an arraylist
//                ArrayList<UserModel> userList = new ArrayList<UserModel>(Arrays.asList(mapper.readValue(f, UserModel[].class)));
//                // add the new stat entry onto the list
//                userList.add(user);
//                // overwrite the contents of the file with the newly extended list
//                mapper.writeValue(f, userList);
//            }
//            else {
//                // create a new file by writing the single entry
//                mapper.writeValue(f, user);
//            }

        } catch (Exception ex)
        {
            // print the stack trace in case there's an error here reading/writing to file
            ex.printStackTrace();
        }
        return user;
    }
}
