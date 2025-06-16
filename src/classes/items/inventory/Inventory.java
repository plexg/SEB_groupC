package classes.items.inventory;

import classes.items.Item;
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
        if (items.isEmpty()) {
            return "No items in the inventory.";
        }
        StringBuilder itemList = new StringBuilder("Items in inventory: ");
        for (int i = 0; i < items.size(); i++) {
            itemList.append(items.get(i).getName());
            if (i < items.size() - 1) {
                itemList.append(", ");
            }
        }
        return itemList.toString();
    }

    public Map<String, Item> getItems() {
        return new HashMap<>(items);
    }
}
