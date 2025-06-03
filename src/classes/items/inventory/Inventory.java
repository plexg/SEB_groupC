package classes.items.inventory;

import classes.items.Item;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;
    private List<InventoryObserver> observers;

    public Inventory() {
        this.items = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
            notifyObservers();
        }
    }

    public boolean removeBrokenItem(Item item) {
        if (item == null || !items.contains(item)) {
            return false;
        }
        boolean removed = items.remove(item);
        if (removed) {
            System.out.println(item.getName() + " has broken and has been removed from the inventory");
            notifyObservers();
        }
        return removed;
    }

    public boolean removeItem(Item item) {
        boolean removed = items.remove(item);
        if (removed) {
            notifyObservers();
        }
        return removed;
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

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
}
