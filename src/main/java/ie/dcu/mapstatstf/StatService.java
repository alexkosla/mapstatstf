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
    public List<StatEntity> listStats()
    {
        // attempt to open up a connection with the database with a hardcoded url, username, and password
        try (Connection conn = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/",
                        "root", "admin"))
        {
            // write a SQL query string and convert it to a java-friendly object
            // query gets back all of the data from the masterstats table in mydb
            PreparedStatement selectStatement = conn.prepareStatement("select * from mydb.masterstats");
            // execute the query and get back the results
            ResultSet rs = selectStatement.executeQuery();

            // create an empty list to store the contents of the result set
            ArrayList<StatEntity> statList = new ArrayList<StatEntity>();

            // while there is another entry in the result set...
            while (rs.next()) {
                // for this row in the result set, get data for the "StatId" column, parse it as a string
                // then convert it to UUID type
                UUID statId = UUID.fromString(rs.getString("StatId"));

                // for this row in the result set, get data for the "LogId" column which is a long
                // then convert it to a string, as javascript will cut off the least significant bits
                // of a long, but not of a string
                String logId = String.valueOf(rs.getLong("LogId"));

                // for this row in the result set, get data for the "Steam64Id" column which is a long
                // then convert it to a string, as javascript will cut off the least significant bits
                // of a long, but not of a string
                String steam64Id = String.valueOf(rs.getLong("Steam64Id"));

                // continue in a similar fashion for the remaining fields in the masterstats table
                String className = rs.getString("Class");
                int mapId = rs.getInt("MapId");
                int kills = rs.getInt("Kills");
                int assists = rs.getInt("Assists");
                int deaths = rs.getInt("Deaths");
                int damage = rs.getInt("Damage");
                int damageTaken = rs.getInt("Damage Taken");
                int secondsPlayed = rs.getInt("SecondsPlayed");

                // take all of these saved temporary fields and use them to create an instance of StatEntity
                // we return a StatEntity rather than a StatModel to get around js quirks with handling longs (this has no longs)
                // append this newly created StatEntity onto the statList
                statList.add(new StatEntity(statId, logId, steam64Id, className, mapId, kills, assists, deaths, damage, damageTaken, secondsPlayed));
            }
            // once we've processed all of the results of the query and added them to the statList, return it
            return statList;
        } catch (Exception ex)
        {
            // print the stack trace in case there's an error here reading/writing to file
            ex.printStackTrace();
        }
        // if anything goes awry, return an empty list
        return Collections.emptyList();
    }

    // function returns a list of all users saved
    public List<UserStatEntity> listUserStats(long steam64Id)
    {
        ArrayList<StatModel> statList = new ArrayList<StatModel>();
        ArrayList<UserModel> userList = new ArrayList<UserModel>();
        ArrayList<UserStatEntity> userStatEntities = new ArrayList<UserStatEntity>();

        // attempt to open up a connection with the database with a hardcoded url, username, and password
        try (Connection conn = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/",
                        "root", "admin"))
        {

            boolean isValid = conn.isValid(0);
            System.out.println("Can I connect to database ?  : " + isValid);

            PreparedStatement selectStatement = conn.prepareStatement("select * from mydb.masterstats");
            ResultSet rs = selectStatement.executeQuery();
            while (rs.next()) {
                UUID statId = UUID.fromString(rs.getString("StatId"));
                long logId = rs.getLong("LogId");
                long steam64Idtemp = rs.getLong("Steam64Id");
                String className = rs.getString("Class");
                int mapId = rs.getInt("MapId");
                int kills = rs.getInt("Kills");
                int assists = rs.getInt("Assists");
                int deaths = rs.getInt("Deaths");
                int damage = rs.getInt("Damage");
                int damageTaken = rs.getInt("Damage Taken");
                int secondsPlayed = rs.getInt("SecondsPlayed");

                statList.add(new StatModel(statId, logId, steam64Idtemp, className, mapId, kills, assists, deaths, damage, damageTaken, secondsPlayed));
            }
            if(statList != null)
            {
                selectStatement = conn.prepareStatement("select * from mydb.users");
                rs = selectStatement.executeQuery();
                while (rs.next()) {
                    long steam64Idtemp = rs.getLong("Steam64Id");
                    String steam3Id = rs.getString("Steam3Id");
                    String username = rs.getString("Username");
                    String preferredClass = rs.getString("PreferredClass");
                    boolean isAdmin = rs.getBoolean("IsAdmin");

                    userList.add(new UserModel(steam64Idtemp, steam3Id, username, preferredClass, isAdmin));
                }

                if(userList != null)
                {
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
    public StatEntity addStat(StatEntity stat)
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
                insertNewStat.setLong(2, Long.getLong(stat.getLogId()));
                insertNewStat.setLong(3, Long.getLong(stat.getSteam64Id()));
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
        } catch (Exception ex)
        {
            // print the stack trace in case there's an error here reading/writing to file
            ex.printStackTrace();
        }
        return stat;
    }
}
