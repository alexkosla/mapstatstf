package ie.dcu.mapstatstf.Service;

import ie.dcu.mapstatstf.Model.StatModel;
import ie.dcu.mapstatstf.Model.UserModel;
import ie.dcu.mapstatstf.Entity.StatEntity;
import ie.dcu.mapstatstf.Entity.UserStatEntity;
import ie.dcu.mapstatstf.Repository.StatRepository;
import ie.dcu.mapstatstf.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatService {
    private StatRepository statRepository;

    private UserRepository userRepository;
    // default constructor
    public StatService ()
    {}
    public StatService(StatRepository statRepository, UserRepository userRepository) {
        this.statRepository = statRepository;
        this.userRepository = userRepository;
    }
    // function returns a list of all users saved

    public List<StatEntity> getAllStats()
    {
        List<StatEntity> statEntityList = new ArrayList<StatEntity>();
        statRepository.findAll().forEach(sm -> statEntityList.add(new StatEntity(sm)));
        return statEntityList;
    }

    // function returns a list of all users saved
    public List<UserStatEntity> listUserStats(long steam64Id, String classNameFilter)
    {
        // define some empty lists to hold objects we get from the database
        List<StatModel> statList = new ArrayList<StatModel>();
        List<UserModel> userList = new ArrayList<UserModel>();
        // define a userstatentity list we'll return to the front-end
        List<UserStatEntity> userStatEntities = new ArrayList<UserStatEntity>();

        statList = statRepository.findAll();

        if(statList != null)
        {
            userList = userRepository.findAll();

            if(userList != null)
            {
                // will return a list of length 1 because we've enforced steam64Id to be unique
                // similar to a one-liner foreach loop that returns the list of all UserModels in the list
                // where their steam64Id matches the steam64Id argument from the controller
                List<UserModel> filteredUsers = userList.stream()
                        .filter(usr ->  usr.getSteam64Id() == steam64Id)
                        .collect(Collectors.toList());
                UserModel user = filteredUsers.get(0);

                // do the same for the statList, filter them for the same steam64Id
                List<StatModel> filteredStats = statList.stream()
                        .filter(st ->  st.getSteam64Id() == steam64Id && st.getClassName() == classNameFilter)
                        .collect(Collectors.toList());

                // go through all of the filteredStats
                for(int i = 0; i < filteredStats.size(); i++)
                {
                    // create an empty userStatEntity to fill manually
//                    UserStatEntity userStat = new UserStatEntity();
                    // iterate through the statList
                    StatModel currStat = filteredStats.get(i);
                    UserStatEntity userStat = new UserStatEntity(user, currStat);

                    // the newly created userstatentity to the userStatEntities list
                    userStatEntities.add(userStat);
                }
            }
        }
        // returned the newly combined entity list
        return userStatEntities;
    }

    // TO-DO: probably not worth keeping as it'll be completely rewritten to work with logs.tf API
    // saves/appends a stat entry to a file
    public StatEntity addStat(StatEntity stat)
    {
        // create a unique identifier for the entry to be saved
        if(stat.getStatId() == null)
            stat.setStatId(UUID.randomUUID());

        // attempt to open up a connection with the database with a hardcoded url, username, and password
        try (Connection conn = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/",
                        "root", "admin"))
        {
            // write a SQL query string and convert it to a java-friendly object
            // query gets back all of the data from the masterstats table in mydb
            PreparedStatement selectStatsById = conn.prepareStatement("select * from mydb.masterstats where statId = ?");
            // set the '?' in the selectStatsById query to be the statId from the stat we're gonna try to add
            selectStatsById.setString(1, String.valueOf(stat.getStatId()));
            // execute the query and get back the results
            ResultSet rs = selectStatsById.executeQuery();

            // if the stat already exists in the database, don't add them
            if(!rs.next())
            {
                // create a sql insert command with blank spaces for each of the fields of the stat object
                PreparedStatement insertNewStat = conn.prepareStatement("INSERT INTO `mydb`.`MasterStats` (`StatId`, `LogID`, `Steam64ID`, `Class`, `MapID`, `Kills`, `Assists`, `Deaths`, `Damage`, `Damage Taken`, `SecondsPlayed`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

                // manually set all of the blank fields in the string with their respective fields from the stat object
                insertNewStat.setString(1, String.valueOf(stat.getStatId()));
                insertNewStat.setLong(2, Long.parseLong(stat.getLogId()));
                insertNewStat.setLong(3, Long.parseLong(stat.getSteam64Id()));
                insertNewStat.setString(4, stat.getClassName());
                insertNewStat.setInt(5, stat.getMapId());
                insertNewStat.setInt(6, stat.getKills());
                insertNewStat.setInt(7, stat.getAssists());
                insertNewStat.setInt(8, stat.getDeaths());
                insertNewStat.setInt(9, stat.getDamage());
                insertNewStat.setInt(10, stat.getDamageTaken());
                insertNewStat.setInt(11, stat.getSeconds());

                // execute the sql statement to add the stat to the db
                insertNewStat.executeUpdate();
            }
        } catch (Exception ex)
        {
            // print the stack trace in case there's an error here reading/writing to file
            ex.printStackTrace();
        }
        return stat;
    }
}
