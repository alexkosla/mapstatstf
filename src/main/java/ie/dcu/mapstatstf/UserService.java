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
        // attempt to open up a connection with the database with a hardcoded url, username, and password
        try (Connection conn = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/",
                        "root", "admin"))
        {

            // write a SQL query string and convert it to a java-friendly object
            // query gets back all of the data from the users table in mydb
            PreparedStatement selectStatement = conn.prepareStatement("select * from mydb.users");
            // execute the query and get back the results
            ResultSet rs = selectStatement.executeQuery();
            // create an empty list to store the contents of the result set
            ArrayList<UserModel> userList = new ArrayList<UserModel>();

            // while there is another entry in the result set...
            while (rs.next()) {
                // for this row in the result set, get data for the "steam64Id" column, parse it as a long
                long steam64Id = rs.getLong("Steam64Id");
                // do similar for the remaining 4 fields
                String steam3Id = rs.getString("Steam3Id");
                String username = rs.getString("Username");
                String preferredClass = rs.getString("PreferredClass");
                boolean isAdmin = rs.getBoolean("IsAdmin");

                // once you have all the fields read from the db, create a new userModel class using them
                // and then add the object to the userList
                userList.add(new UserModel(steam64Id, steam3Id, username, preferredClass, isAdmin));
            }
            // once we've processed all of the results of the query and added them to the userList, return it
            return userList;
        } catch (Exception ex)
        {
            // print the stack trace in case there's an error here reading/writing to file
            ex.printStackTrace();
        }
        // if anything goes awry, return an empty list
        return Collections.emptyList();
    }

    // saves/appends a user's info to a file
    public UserModel addUser(UserModel user)
    {
        // create a unique identifier for the entry to be saved
        try (Connection conn = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/",
                        "root", "admin"))
        {
            // write a SQL query string and convert it to a java-friendly object
            // query gets back all of the data from the users table in mydb
            PreparedStatement selectUsersById = conn.prepareStatement("select * from mydb.users where steam64Id = ?");

            // set the '?' in the selectUsersById query to be the statId from the stat we're gonna try to add
            selectUsersById.setString(1, String.valueOf(user.getSteam64Id()));
            // execute the query and get back the results
            ResultSet rs = selectUsersById.executeQuery();

            if(!rs.next())
            {
                // create a sql insert command with blank spaces for each of the fields of the user object
                PreparedStatement insertNewUser = conn.prepareStatement("INSERT INTO `mydb`.`Users` (`Steam64ID`, `Steam3ID`, `Username`, `PreferredClass`, `IsAdmin`) VALUES (?, ?, ?, ?, ?);");

                // manually set all of the blank fields in the string with their respective fields from the user object
                insertNewUser.setLong(1, user.getSteam64Id());
                insertNewUser.setString(2, user.getSteam3Id());
                insertNewUser.setString(3, user.getUsername());
                insertNewUser.setString(4, user.getPreferredClass());
                insertNewUser.setBoolean(5, user.isAdmin());

                // execute the sql statement to add the user to the db
                insertNewUser.executeUpdate();
            }

        } catch (Exception ex)
        {
            // print the stack trace in case there's an error here reading/writing to file
            ex.printStackTrace();
        }
        return user;
    }
}
