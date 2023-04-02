package ie.dcu.mapstatstf;
//import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UserModel {
//    @NotNull
    private long steam64Id;

//    @NotNull
    private String steam3Id;

    private String username;

    private UUID preferredClass;

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

    public UUID getPreferredClass() {
        return preferredClass;
    }

    public void setPreferredClass(UUID preferredClass) {
        this.preferredClass = preferredClass;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
