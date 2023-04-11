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

        } catch (Exception ex)
        {
            // print the stack trace in case there's an error here reading/writing to file
            ex.printStackTrace();
        }
        return user;
    }
}
