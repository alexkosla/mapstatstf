package ie.dcu.mapstatstf.Service;

import ie.dcu.mapstatstf.Entity.UserStatEntity;
import ie.dcu.mapstatstf.Model.UserModel;
import ie.dcu.mapstatstf.Repository.UserRepository;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.*;

@Service
public class UserService {
    private UserRepository userRepository;

    // default constructor
    public UserService() {
    }

    // function returns a list of all users saved
    public List<UserModel> listUsers()
    {
        return userRepository.findAll();
    }

    // TO-DO: rework this much later on, can probably be deleted now
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
