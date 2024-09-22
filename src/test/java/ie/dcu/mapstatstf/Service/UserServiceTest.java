package ie.dcu.mapstatstf.Service;

import ie.dcu.mapstatstf.Model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserModel userA;
    UserModel userB;

    UserService userService;

    @BeforeEach
    void setUp() {
        this.userA = new UserModel(999, "steam3Id", "usernameA", "sniper", false);
        this.userB = new UserModel(9999, "steam3Id1", "usernameB", "scout", true);

        userService = new UserService();
    }

}