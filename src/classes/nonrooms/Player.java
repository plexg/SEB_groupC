package classes.nonrooms;

import classes.items.Item;
import classes.rooms.Room;
import classes.items.inventory.Inventory;
import classes.database.Database;
import classes.joker.Joker;

import java.sql.SQLException;
import java.util.List;

public class Player {
    private int id;
    private int progress;
    private int hp;
    private Room currentroom;
    private String name;
    public Inventory inventory;
    private Room previousRoom;
    Database database = new Database();

    // Constructor
    public Player(int id, int hp, Room room, String name, List<Item> items) {
        this.id = id;
        this.progress = 0;
        this.hp = hp;
        this.currentroom = room;
        this.name = name;
        this.inventory = new Inventory();
        this.previousRoom = null;
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
        return currentroom;
    }

    public void setRoom(Room room) {
        if (this.currentroom != null) {
            this.previousRoom = this.currentroom;
        }
        this.currentroom = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setPreviousRoom(Room previousRoom) {
        this.previousRoom = previousRoom;
    }

    public Room getPreviousRoom() {
        return previousRoom;
    }

    // Methods
    public String getStatus() {
        try {
            return "Player Status:\n" +
                    "Progress: " + progress + "\n" +
                    "HP: " + hp + "\n" +
                    "Room: " + (currentroom != null ? currentroom.getClass().getSimpleName() : "None") + '\n' +
                    "Inventory: " + '\n' + inventory.loadFromDatabase(getId(), database.getConnection()) +
                    "\nType 'use <item>' to heal yourself with that item or use your Joker.\n";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addItem(Item item) {
        inventory.addItem(item);
    }

    public String getItems() {
        return inventory.listItems();
    }

    public void useJoker(String jokerName, Room room) {
        Joker joker = (Joker) inventory.getItem(jokerName);
        if (joker != null) {
            joker.useIn(room);
        } else {
            System.out.println("You don't have this joker in your inventory.");
        }
    }
}