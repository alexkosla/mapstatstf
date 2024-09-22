package ie.dcu.mapstatstf.Entity;

import ie.dcu.mapstatstf.Model.StatModel;
import ie.dcu.mapstatstf.Model.UserModel;

public class UserStatEntity {
    public UserStatEntity() {}

    public UserStatEntity(UserModel user, StatModel currStat) {
        this.setLogId(currStat.getLogId());
        this.setUsername(user.getUsername());
        this.setPreferredClass(user.getPreferredClass());
        this.setMapId(currStat.getMapId());
        this.setClassName(currStat.getClassName());
        this.setKills(currStat.getKills());
        this.setAssists(currStat.getAssists());
        this.setDeaths(currStat.getDeaths());
        this.setDamage(currStat.getDamage());
        this.setDamageTaken(currStat.getDamageTaken());
        this.setSeconds(currStat.getSeconds());
    }

    private long logId;
    private String username;
    private String preferredClass;
    private int mapId;
    private String className;
    private int kills;
    private int assists;
    private int deaths;
    private int damage;
    private int damageTaken;
    private int seconds;

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPreferredClass() {
        return preferredClass;
    }

    public void setPreferredClass(String preferredClass) {
        this.preferredClass = preferredClass;
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
