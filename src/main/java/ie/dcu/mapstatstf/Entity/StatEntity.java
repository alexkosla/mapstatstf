package ie.dcu.mapstatstf.Entity;

import ie.dcu.mapstatstf.Model.StatModel;

import java.util.UUID;

public class StatEntity {
    // primary key of statId, for easy GET calls rather than needing to specify both logId and steam64Id
    private UUID statId;

    // id of the match in the logs.tf database, for eventual integration with logs.tf API
    private String logId;

    // String to store the 64-bit steamId number better on the front-end (js doesn't represent it well as a long)
    // foreign key to user table
    // used by various apis integrated with a popular gaming service called steam
    private String steam64Id;

    // Id of the map in map table of database
    // foreign key to map table
    private int mapId;

    // name of the team fortress 2 class played in the match
    private String className;

    // # of kills the player had in the match
    private int kills;

    // # of assists the player had in the match
    private int assists;

    // # of deaths the player had in the match
    private int deaths;

    // # of damage the player dealt in the match
    private int damage;

    // # of damage the player received in the match
    private int damageTaken;

    // length the player played in the match in seconds
    private int seconds;

    public StatEntity(StatModel statModel)
    {
        this.statId = statModel.getStatId();
        this.logId = String.valueOf(statModel.getLogId());
        this.steam64Id = String.valueOf(statModel.getSteam64Id());
        this.mapId = statModel.getMapId();
        this.className = statModel.getClassName();
        this.kills = statModel.getKills();
        this.assists = statModel.getAssists();
        this.deaths = statModel.getDeaths();
        this.damage = statModel.getDamage();
        this.damageTaken = statModel.getDamageTaken();
        this.seconds = statModel.getSeconds();
    }

    public StatEntity(UUID statId, String logId, String steam64Id, String className, int mapId, int kills, int assists, int deaths, int damage, int damageTaken, int seconds) {
        this.statId = statId;
        this.logId = logId;
        this.steam64Id = steam64Id;
        this.mapId = mapId;
        this.className = className;
        this.kills = kills;
        this.assists = assists;
        this.deaths = deaths;
        this.damage = damage;
        this.damageTaken = damageTaken;
        this.seconds = seconds;
    }

    public UUID getStatId() {
        return statId;
    }

    public void setStatId(UUID statId) {
        this.statId = statId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getSteam64Id() {
        return steam64Id;
    }

    public void setSteam64Id(String steam64Id) {
        this.steam64Id = steam64Id;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamageTaken() {
        return damageTaken;
    }

    public void setDamageTaken(int damageTaken) {
        this.damageTaken = damageTaken;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
