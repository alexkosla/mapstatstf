package ie.dcu.mapstatstf;
//import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class StatModel {

    private UUID statId;
    private long logId;
    private long steam64Id;

    private int mapId;

    private String className;

    private int kills;
    private int assists;
    private int deaths;
    private int damage;
    private int damageTaken;
    private int seconds;

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
        System.out.println("************* setting steam64Id which is: "+steam64Id+"****************");
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
