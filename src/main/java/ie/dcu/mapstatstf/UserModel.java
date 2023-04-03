package ie.dcu.mapstatstf;
public class UserModel {
    // long to store the 64-bit steamId number
    // primary key on user table
    // used by various apis integrated with a popular gaming service called steam
    private long steam64Id;

    // another steam user id format, useful to store rather than needing to convert lazily
    private String steam3Id;

    // user-entered username on mapstats.tf
    private String username;

    // name of the user's preferred class in the game of tf2
    private String preferredClass;

    // true if user is an admin
    private boolean isAdmin;

    public long getSteam64Id() {
        return steam64Id;
    }

    public void setSteam64Id(long steam64Id) {
        this.steam64Id = steam64Id;
    }

    public String getSteam3Id() {
        return steam3Id;
    }

    public void setSteam3Id(String steam3Id) {
        this.steam3Id = steam3Id;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
