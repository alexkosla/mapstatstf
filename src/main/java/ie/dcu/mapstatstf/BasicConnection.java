package ie.dcu.mapstatstf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * @created ${DAY}/${MONTH}/${YEAR}
 * @project ${PROJECT_NAME}
 * @author Katarzyna Fidos
 */
public class BasicConnection {
    public static void main(String[] args) throws SQLException {
        try {

            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/",
                            "root", "admin");
            boolean isValid = conn.isValid(0);
            System.out.println("Can I connect to database ?  : " + isValid);
//
//
            PreparedStatement selectStatement = conn.prepareStatement("select * from mydb.maps");
//            selectStatement.setString(1, "Ali");
            ResultSet rs = selectStatement.executeQuery();
            while (rs.next()) {
                int mapId = rs.getInt("MapId");
                String mapNameShort = rs.getString("MapNameShort");
                String ImgLocation = rs.getString("ImgLocation");
                String MapNameFull = rs.getString("MapNameFull");
                System.out.println("mapId: " + mapId + ", " + "mapNameShort: " + mapNameShort );
                System.out.println("ImgLocation: " + ImgLocation + ", " + "MapNameFull: " + MapNameFull );
            }


            // String a = "'Ali'";
//            String sqlInjection = "'John' OR '1=1'";
//            String query = "SELECT * FROM lectures l WHERE l.author = " + sqlInjection;
//            try (Statement stmt = conn.createStatement()) {
//                ResultSet resultSet = stmt.executeQuery(query);
//                while (resultSet.next()) {
//                    String auhtor = resultSet.getString("author");
//                    String subject = resultSet.getString("subject");
//                    System.out.println("Lecture Author: " + auhtor + ", " + "lecture Subject: " + subject );
//                }
//            } catch (SQLException e) {
//                System.out.println(e);
//            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


}
