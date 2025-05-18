package classes;

import classes.rooms.Room;
import classes.rooms.RoomFactory;
import classes.Player;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/seb";
    private static final String USER = "root";
    private static final String PASSWORD = "Koe!90!KJ!80";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Player loadPlayer(String name) {
        String query = "SELECT * FROM playerprogress WHERE name = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int hp = rs.getInt("hp");
                String currentRoom = rs.getString("current_room");
                Player player = new Player(id, hp, null, name);
                Room room = RoomFactory.getRoomByName(currentRoom, player);
                player.setRoom(room);
                return player;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Player not found in the database.");
    }

    public void savePlayer(Player player) {
        if (player.getName() == null || player.getName().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty.");
        }
        String query = "INSERT INTO playerprogress (name, hp, current_room) VALUES (?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE hp = VALUES(hp), current_room = VALUES(current_room)";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, player.getName());
            stmt.setInt(2, player.getHp());
            stmt.setString(3, player.getRoom() != null ? player.getRoom().getName() : null);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Player data saved successfully.");
            } else {
                System.out.println("No rows affected. Check if the player exists in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlayer(String name) {
        String query = "DELETE FROM playerprogress WHERE name = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}