package classes.items.inventory;

import classes.items.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public boolean hasItem(String name) {
        return items.containsKey(name);
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

    public boolean removeItem(String itemName) {
        if (items.containsKey(itemName)) {
            items.remove(itemName);
            notifyObservers();
            return true;
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

    public Map<String, Item> getItems() {
        return new HashMap<>(items);
    }

    public void saveToDatabase(int playerId, Connection connection) {
        String query = "INSERT INTO PlayerInventory (player_id, BoxCutter, Staplergun, Pencil, Cup_of_Coffee, Donut, White_Key, Green_Key, Purple_Key, Gold_Key) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE BoxCutter = VALUES(BoxCutter), Staplergun = VALUES(Staplergun), Pencil = VALUES(Pencil), " +
                       "Cup_of_Coffee = VALUES(Cup_of_Coffee), Donut = VALUES(Donut), White_Key = VALUES(White_Key), " +
                       "Green_Key = VALUES(Green_Key), Purple_Key = VALUES(Purple_Key), Gold_Key = VALUES(Gold_Key)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, playerId);
            stmt.setBoolean(2, items.containsKey("BoxCutter"));
            stmt.setBoolean(3, items.containsKey("Staplergun"));
            stmt.setBoolean(4, items.containsKey("Pencil"));
            stmt.setBoolean(5, items.containsKey("Cup_of_Coffee"));
            stmt.setBoolean(6, items.containsKey("Donut"));
            stmt.setBoolean(7, items.containsKey("White_Key"));
            stmt.setBoolean(8, items.containsKey("Green_Key"));
            stmt.setBoolean(9, items.containsKey("Purple_Key"));
            stmt.setBoolean(10, items.containsKey("Gold_Key"));

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}