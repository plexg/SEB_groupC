package classes;

import classes.rooms.Room;

public class Player {
    private int progress;
    private int hp;
    private Room room;

    // constructor
    public Player(int progress, int hp, Room room) {
        this.progress = 0;
        this.hp = 100;
        this.room = null;
    }

    // getters and setters
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

    // methods
    public void getStatus() {
        System.out.println("Player Status");
        System.out.println("Progress: " + progress);
        System.out.println("HP: " + hp);
        System.out.println("Room: " + room.getClass().getSimpleName());
    }

}
