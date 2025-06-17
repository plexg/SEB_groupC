package classes.items.inventory;

import classes.items.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import classes.items.ItemFactory;
import classes.joker.Joker;

public class Inventory {
    private HashMap<String, Item> items;
    private List<InventoryObserver> observers;

    public Inventory() {
        this.items = new HashMap<>();
        this.observers = new ArrayList<>();
    }

    public void addItem(Item item) {
        if (item != null) {
            items.put(item.getName(), item);
            notifyObservers();
        }
    }

    public Item getItem(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        return items.get(name);
    }

    public boolean hasItem(String name, int playerId, Connection connection) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String query = "SELECT COUNT(*) FROM playerinventory WHERE player_id = ? AND item_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, playerId);
            stmt.setString(2, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeBrokenItem(Item item) {
        if (item == null || !items.containsKey(item.getName())) {
            return false;
        }
        items.remove(item.getName());
        System.out.println(item.getName() + " has broken and has been removed from the inventory");
        notifyObservers();
        return true;
    }

    public boolean removeItem(String itemName, int playerId, Connection connection) {
        if (hasItem(itemName, playerId, connection)) {
            items.remove(itemName);
            String deleteQuery = "DELETE FROM playerinventory WHERE player_id = ? AND item_name = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
                stmt.setInt(1, playerId);
                stmt.setString(2, itemName);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    notifyObservers();
                    return true;
                } else {
                    System.out.println("Item not found in the database.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Item not found in inventory.");
        }
        return false;
    }

    public void addObserver(InventoryObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(InventoryObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (InventoryObserver observer : observers) {
            observer.onInventoryChanged();
        }
    }

    public String listItems() {
        StringBuilder itemsList = new StringBuilder();
        for (String key : items.keySet()) {
            Item item = items.get(key);
            if (item != null) {
                itemsList.append(item.getName()).append("\n");
            } else {
                itemsList.append("Item with key '").append(key).append("' not found.\n");
            }
        }
        return itemsList.toString();
    }

    public String loadFromDatabase(int playerId, Connection connection) {
        StringBuilder result = new StringBuilder();
        String query = "SELECT item_name FROM playerinventory WHERE player_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, playerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String itemName = rs.getString("item_name");
                    Item item = ItemFactory.createItem(itemName);
                    if (item != null) {
                        items.put(itemName, item);
                        result.append(itemName).append(", ");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error loading items from database.";
        }
        return result.length() > 0 ? result.substring(0, result.length() - 2) : "No items found.";
    }

    public void saveToDatabase(int playerId, Connection connection) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM playerinventory WHERE player_id = ? AND item_name = ?";
        String insertQuery = "INSERT INTO playerinventory (player_id, item_name) VALUES (?, ?)";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
             PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {

            for (Item item : items.values()) {
                checkStmt.setInt(1, playerId);
                checkStmt.setString(2, item.getName());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        continue;
                    }
                }

                insertStmt.setInt(1, playerId);
                insertStmt.setString(2, item.getName());
                insertStmt.addBatch();
            }

            insertStmt.executeBatch();
        }
    }
}