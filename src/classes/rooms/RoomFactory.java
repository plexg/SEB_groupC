package classes.rooms;

import classes.Player;

public class RoomFactory {
    public static Room getRoomByName(String roomName, Player player) {
        if (roomName == null || roomName.isEmpty()) {
            throw new IllegalArgumentException("Room name cannot be null or empty.");
        }
        switch (roomName) {
            case "StartRoom":
                return new StartRoom(player);
            case "DailyScrumRoom":
                return new DailyScrumRoom(player);
            default:
                throw new IllegalArgumentException("Invalid room name: " + roomName);
        }
    }
}