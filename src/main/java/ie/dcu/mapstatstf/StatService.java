package ie.dcu.mapstatstf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatService {
    // default constructor
    public StatService() {
    }
    // function returns a list of all users saved
    public List<StatModel> listStats()
    {
        try (Connection conn = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/",
                        "root", "admin"))
        {

            boolean isValid = conn.isValid(0);
            System.out.println("Can I connect to database ?  : " + isValid);

            PreparedStatement selectStatement = conn.prepareStatement("select * from mydb.masterstats");
            ResultSet rs = selectStatement.executeQuery();
            ArrayList<StatModel> statList = new ArrayList<StatModel>();
            while (rs.next()) {
                UUID statId = UUID.fromString(rs.getString("StatId"));
                long logId = rs.getLong("LogId");
                long steam64Id = rs.getLong("Steam64Id");
                String className = rs.getString("Class");
                int mapId = rs.getInt("MapId");
                int kills = rs.getInt("Kills");
                int assists = rs.getInt("Assists");
                int deaths = rs.getInt("Deaths");
                int damage = rs.getInt("Damage");
                int damageTaken = rs.getInt("Damage Taken");
                int secondsPlayed = rs.getInt("SecondsPlayed");

                statList.add(new StatModel(statId, logId, steam64Id, className, mapId, kills, assists, deaths, damage, damageTaken, secondsPlayed));
            }
            return statList;
        } catch (Exception ex)
        {
            // print the stack trace in case there's an error here reading/writing to file
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    // function returns a list of all users saved
    public List<UserStatEntity> listUserStats(long steam64Id)
    {
        ArrayList<StatModel> statList = null;
        ArrayList<UserModel> userList = null;
        ArrayList<UserStatEntity> userStatEntities = new ArrayList<UserStatEntity>();
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
                statList = new ArrayList<StatModel>(Arrays.asList(mapper.readValue(f, StatModel[].class)));
                // add the new stat entry onto the list
            }

            if(statList != null)
            {
                // set the relative path of the location of the users json file
                fileLocation = "./src/main/resources/users.json";

                // check if the file already exists at the location specified
                f = new File(fileLocation);
                if(f.isFile()) {
                    // if the file does exist, use the mapper to read the value and parse it as an arraylist
                    userList = new ArrayList<UserModel>(Arrays.asList(mapper.readValue(f, UserModel[].class)));
                }

                if(userList != null)
                {
                    // https://stackoverflow.com/questions/19396944/what-is-the-java-equivalent-for-enumerable-select-with-lambdas-in-c
                    // will return a list of length 1 because we've enforced steam64Id to be unique
                    // similar to a one-liner foreach loop that returns the list of all UserModels in the list
                    // where their steam64Id matches the steam64Id argument from the controller
                    List<UserModel> filteredUsers = userList.stream()
                            .filter(usr ->  usr.getSteam64Id() == steam64Id)
                            .collect(Collectors.toList());
                    UserModel user = filteredUsers.get(0);

                    List<StatModel> filteredStats = statList.stream()
                            .filter(st ->  st.getSteam64Id() == steam64Id)
                            .collect(Collectors.toList());

                    for(int i = 0; i < filteredStats.size(); i++)
                    {
                        // create an empty userStatEntity to fill manually
                        UserStatEntity userStat = new UserStatEntity();
                        // iterate through the statList
                        StatModel currStat = filteredStats.get(i);

                        // manually set all the fields of userStatEntity using data from both UserModel and StatModel
                        userStat.setLogId(currStat.getLogId());
                        userStat.setUsername(user.getUsername());
                        userStat.setPreferredClass(user.getPreferredClass());
                        userStat.setMapId(currStat.getMapId());
                        userStat.setClassName(currStat.getClassName());
                        userStat.setKills(currStat.getKills());
                        userStat.setAssists(currStat.getAssists());
                        userStat.setDeaths(currStat.getDeaths());
                        userStat.setDamage(currStat.getDamage());
                        userStat.setDamageTaken(currStat.getDamageTaken());
                        userStat.setSeconds(currStat.getSeconds());

                        userStatEntities.add(userStat);
                    }
                    return userStatEntities;
                }
            }

        } catch (Exception ex)
        {
            // print the stack trace in case there's an error here reading/writing to file
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    // saves/appends a stat entry to a file
    public StatModel addStat(StatModel stat)
    {
        // create a unique identifier for the entry to be saved
        if(stat.getStatId() == null)
            stat.setStatId(UUID.randomUUID());

        try (Connection conn = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/",
                        "root", "admin")){
            boolean isValid = conn.isValid(0);
            System.out.println("Can I connect to database ?  : " + isValid);

            PreparedStatement selectStatsById = conn.prepareStatement("select * from mydb.masterstats where StatId = ?");
            selectStatsById.setString(1, String.valueOf(stat.getSteam64Id()));
            ResultSet rs = selectStatsById.executeQuery();
            if(rs.next())
            {
                // case where user already exists
                return stat;
            }
            else
            {
                PreparedStatement insertNewStat = conn.prepareStatement("INSERT INTO `mydb`.`MasterStats` (`StatId`, `LogID`, `Steam64ID`, `Class`, `MapID`, `Kills`, `Assists`, `Deaths`, `Damage`, `Damage Taken`, `SecondsPlayed`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

                insertNewStat.setString(1, String.valueOf(stat.getStatId()));
                insertNewStat.setLong(2, stat.getLogId());
                insertNewStat.setLong(3, stat.getSteam64Id());
                insertNewStat.setString(4, stat.getClassName());
                insertNewStat.setInt(5, stat.getMapId());
                insertNewStat.setInt(6, stat.getKills());
                insertNewStat.setInt(7, stat.getAssists());
                insertNewStat.setInt(8, stat.getDeaths());
                insertNewStat.setInt(9, stat.getDamage());
                insertNewStat.setInt(10, stat.getDamageTaken());
                insertNewStat.setInt(11, stat.getSeconds());
                insertNewStat.executeUpdate();
                return stat;
            }

//        try{
//            // set the relative path of the location of the json file
//            String fileLocation = "./src/main/resources/stats.json";
//            // create a jackson object mapper to use for converting an object to a json file
//            ObjectMapper mapper = new ObjectMapper();
//            // tell the mapper to parse a single value as an array to avoid errors
//            mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
//
//            // check if the file already exists at the location specified
//            File f = new File(fileLocation);
//            if(f.isFile()) {
//                // if the file does exist, use the mapper to read the value and parse it as an arraylist
//                ArrayList<StatModel> statList = new ArrayList<StatModel>(Arrays.asList(mapper.readValue(f, StatModel[].class)));
//                // add the new stat entry onto the list
//                statList.add(stat);
//                // overwrite the contents of the file with the newly extended list
//                mapper.writeValue(f, statList);
//            }
//            else {
//                // create a new file by writing the single entry
//                mapper.writeValue(f, stat);
//            }

        } catch (Exception ex)
        {
            // print the stack trace in case there's an error here reading/writing to file
            ex.printStackTrace();
        }
        return stat;
    }
}
