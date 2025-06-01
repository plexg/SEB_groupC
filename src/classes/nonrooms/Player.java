package classes.nonrooms;

import classes.items.Item;
import classes.rooms.Room;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private int id;
    private int progress;
    private int hp;
    private Room room;
    private String name;
    List<Item> items = new ArrayList<>();

    // Constructor
    public Player(int id, int hp, Room room, String name, List<Item> items) {
        this.id = id;
        this.progress = 0;
        this.hp = hp;
        this.room = room;
        this.name = name;
        this.items = items;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Methods
    public String getStatus() {
        return "Player Status:\n" +
                "Progress: " + progress + "\n" +
                "HP: " + hp + "\n" +
                "Room: " + (room != null ? room.getClass().getSimpleName() : "None") + '\n' +
                getItems();

    }

    public void addItem(Item item) {
        items.add(item);
    }

    public String getItems() {
        if (items.isEmpty()) {
            return "No items in the inventory.";
        } else {
            StringBuilder res = new StringBuilder("Items in inventory: ");
            for (int i = 0; i < items.size(); i++) {
                res.append(items.get(i).getClass().getSimpleName());
                if (i < items.size() - 1) {
                    res.append(", ");
                }
            }
            return res.toString();
        }
    }
}