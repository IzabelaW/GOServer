package Game;

import Listeners.IGameView;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which coordinates the game. Observes initial actions of human.
 */
public class Game implements IGameView {
    /**
     * List of rooms.
     */
    private ArrayList<Room> rooms;

    public Game() {
        rooms = new ArrayList<Room>();

    }

    /**
     * Engages start of the game for each client. Makes socket on a specific port, accepts clients, starts their threads,
     * then calls methods that take care of starting game.
     * @throws IOException
     */
    public void startGame() throws IOException {

        ServerSocket listener = new ServerSocket(8901);

        try {
            while (true) {
                Human player1 = new Human(listener.accept());
                player1.attachView(this);
                player1.start();
            }

        } finally {
            listener.close();
        }
    }


    @Override
    public synchronized void onCreateNewRoom(Room room) {
        rooms.add(room);
        room.setIndex(rooms.indexOf(room));
    }

    @Override
    public synchronized Room onJoinHumanToRoom(int numberOfRoom, Human human) {
        Room chosenRoom = rooms.get(numberOfRoom);
        chosenRoom.setJoiner(human);
        human.setIndexOfRoom(chosenRoom.getIndex());
        human.setPlayerColor(PlayerColor.BLACK);
        human.setOpponent(chosenRoom.getInitiator());
        chosenRoom.getInitiator().setOpponent(human);
        return chosenRoom;
    }

    /**
     * Parse the list of indexes to String.
     * @return parsed list of indexes.
     */
    @Override
    public synchronized List<String> getListOfIndexesToString(){
        List<String> stringListOfIndexes = new ArrayList<>();
        for (Room room : rooms){
            stringListOfIndexes.add(Integer.toString(room.getIndex()));
        }

        return stringListOfIndexes;
    }

    /**
     * Parse the list of initiators logins to String.
     * @return parsed list of initiators' logins.
     */
    @Override
    public synchronized List<String> getListOfInitiatorsLoginsToString(){
        List<String> stringListOfPlayer1Logins = new ArrayList<>();
        for (Room room : rooms){
            stringListOfPlayer1Logins.add(room.getInitiator().getLogin().toString());
        }

        return stringListOfPlayer1Logins;
    }

    /**
     * Parse the list of joiners logins to String.
     * @return parsed list of joiners' logins.
     */
    @Override
    public synchronized List<String> getListOfJoinersLoginsToString(){
        List<String> stringListOfPlayer2Logins = new ArrayList<>();
        for (Room room: rooms){
            if(room.getJoiner() != null)
                stringListOfPlayer2Logins.add(room.getJoiner().getLogin().toString());
            else
                stringListOfPlayer2Logins.add("-");
        }

        return stringListOfPlayer2Logins;
    }
}