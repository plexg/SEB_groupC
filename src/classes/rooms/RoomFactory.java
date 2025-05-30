package classes.rooms;

    import classes.nonrooms.Player;
    import classes.database.Database;
    import classes.rooms.rooms.*;

public class  RoomFactory {

        public static Room createRoom(String roomName, Player player, Database database) {
            if (roomName == null || roomName.isEmpty()) {
                throw new IllegalArgumentException("Room name cannot be null or empty.");
            }
            if (player == null) {
                throw new IllegalArgumentException("Player cannot be null.");
            }
            if (database == null) {
                throw new IllegalArgumentException("Database cannot be null.");
            }

            switch (roomName) {
                case "StartRoom":
                    return new StartRoom(player);
                case "DailyScrumRoom":
                    return new DailyScrumRoom(player, database);
                case "SprintPlanningRoom":
                    return new SprintPlanningRoom(player, database);
                case "SprintRetrospectiveRoom":
                    return new SprintRetrospectiveRoom(player, database);
                case "SprintReviewRoom":
                    return new SprintReviewRoom(player, database);
                case "ScrumBoardRoom":
                    return new ScrumBoardRoom(player, database);
                case "FinalRoom":
                    return new FinalRoom();
                default:
                    throw new IllegalArgumentException("Invalid room name: " + roomName);
            }
        }
    }