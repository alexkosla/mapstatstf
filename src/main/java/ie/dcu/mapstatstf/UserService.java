package ie.dcu.mapstatstf;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, UserModel> users = new HashMap<>();
    public UserService() {
    }

    public UserModel createUser(String username, String steamId, boolean isAdmin)
    {
        UserModel user = new UserModel();
        user.setSteam3Id(steamId);
        user.setUsername(username);
        user.setAdmin(isAdmin);
        return user;
    }

    public UserModel addUser(UserModel user)
    {
        try{
            users.put(user.getSteam3Id(), user);
            ObjectMapper mapper = new ObjectMapper();

            mapper.writeValue(Paths.get("users.json").toFile(), users);
        } catch (Exception ex)
        {
            System.out.println("unable to stringify user '" + user.getUsername() + "' to .json file");
            ex.printStackTrace();
        }
    }
}
