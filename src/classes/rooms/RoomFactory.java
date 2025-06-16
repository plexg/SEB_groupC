package classes.rooms;

    import classes.nonrooms.Game;
    import classes.nonrooms.Player;
    import classes.database.Database;
    import classes.rooms.rooms.*;

public class  RoomFactory {

    Game game;

        public static Room createRoom(String roomName, Player player, Database database, Game game) {
            if (roomName == null || roomName.isEmpty()) {
                throw new IllegalArgumentException("Room name cannot be null or empty.");
            }
            if (player == null) {
                throw new IllegalArgumentException("Player cannot be null.");
            }
            if (database == null) {
                throw new IllegalArgumentException("Database cannot be null.");
            }
            if (game == null) {
                throw new IllegalArgumentException("Game cannot be null.");
            }

            switch (roomName) {
                case "StartRoom":
                    return new StartRoom(player);
                case "DailyScrumRoom":
                    return new DailyScrumRoom(player, database, game);
                case "SprintPlanningRoom":
                    return new SprintPlanningRoom(player, database, game);
                case "SprintRetrospectiveRoom":
                    return new SprintRetrospectiveRoom(player, database, game);
                case "SprintReviewRoom":
                    return new SprintReviewRoom(player, database, new Challenge.MultipleChoiceChallenge(), game);
                case "ScrumBoardRoom":
                    return new ScrumBoardRoom(player, database, game);
                case "FinalRoom":
                    return new FinalRoom(player, database, game);
                default:
                    throw new IllegalArgumentException("Invalid room name: " + roomName);
            }
        }
    }