package classes.database;

import classes.items.Item;
import classes.nonrooms.Player;
import classes.rooms.Room;
import classes.rooms.RoomFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/seb";
    private static final String USER = "root";
    private static final String PASSWORD = "Koe!90!KJ!80";
    List<Item> items = new ArrayList<Item>();
    private Connection connection;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Connection connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public Player loadPlayer(String name) {
        String query = "SELECT id, name, hp, current_room FROM playerprogress WHERE name = ?";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int hp = rs.getInt("hp");
                String currentRoom = rs.getString("current_room");
                Room room = currentRoom != null ? RoomFactory.createRoom(currentRoom, new Player(id, hp, null, name, items), this) : null;                return new Player(id, hp, room, name, items);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean savePlayer(Player player) {
        if (player.getName() == null || player.getName().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty.");
        }
        String query = "INSERT INTO playerprogress (id, name, hp, current_room) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE hp = VALUES(hp), current_room = VALUES(current_room)";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, player.getId());
            stmt.setString(2, player.getName());
            stmt.setInt(3, player.getHp());
            stmt.setString(4, player.getRoom() != null ? player.getRoom().getName() : null);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePlayer(Player player) {
        if (player.getName() == null || player.getName().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty.");
        }
        String query = "UPDATE playerprogress SET hp = ?, current_room = ? WHERE name = ?";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, player.getHp());
            stmt.setString(2, player.getRoom() != null ? player.getRoom().getName() : null);
            stmt.setString(3, player.getName());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    public void updateRoomCompletion(String playerName, String roomColumn, boolean status) {
        String query = "UPDATE playerprogress SET " + roomColumn + " = ? WHERE name = ?";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBoolean(1, status);
            stmt.setString(2, playerName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isRoomCompleted(String playerName, String roomColumn) {
        String query = "SELECT " + roomColumn + " FROM playerprogress WHERE name = ?";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(roomColumn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getPlayerRoom(String playerName) {
        String query = "SELECT current_room FROM playerprogress WHERE name = ?";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("current_room");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePlayerRoom(String playerName, String roomName) {
        String query = "UPDATE playerprogress SET current_room = ? WHERE name = ?";
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, roomName);
            stmt.setString(2, playerName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() throws Exception {
        connect();
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");

            // Query to get all table names
            ResultSet rs = stmt.executeQuery("SHOW TABLES");
            List<String> tableNames = new ArrayList<>();
            while (rs.next()) {
                tableNames.add(rs.getString(1));
            }

            // Truncate each table
            for (String tableName : tableNames) {
                try (Statement truncateStmt = connection.createStatement()) {
                    truncateStmt.executeUpdate("TRUNCATE TABLE " + tableName);
                }
            }

            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
            System.out.println("All data in the database has been deleted.");
        }
    }
}