package ie.dcu.mapstatstf;
//import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class StatModel {

    // primary key of statId, for easy GET calls rather than needing to specify both logId and steam64Id
    private UUID statId;

    // id of the match in the logs.tf database, for eventual integration with logs.tf API
    private long logId;

    // long to store the 64-bit steamId number
    // foreign key to user table
    // used by various apis integrated with a popular gaming service called steam
    private long steam64Id;

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

    public StatModel(UUID statId, long logId, long steam64Id, String className, int mapId, int kills, int assists, int deaths, int damage, int damageTaken, int seconds) {
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

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public long getSteam64Id() {
        return steam64Id;
    }

    public void setSteam64Id(long steam64Id) {
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
