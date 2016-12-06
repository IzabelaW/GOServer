package Game;

import Listeners.IHumanStartedGameListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Class which coordinates the game. Observes initial actions of human.
 */
public class Game implements IHumanStartedGameListener {
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

        ServerSocket listener = new ServerSocket(8900);

        try {
            while (true) {
                Human player1 = new Human(listener.accept());
                player1.start();
                makeHumanLogIn(player1);
                makeHumanChooseOpponent(player1);
            }

        } finally {
            listener.close();
        }
    }

    /**
     * Delegates request to log to the particular Human.
     * @param human
     */
    private void makeHumanLogIn(Human human){
        try {
            human.logIn(this);
        } catch (SocketException e) {
            human.disconnectPlayer();
        }
    }

    /**
     * Delegates request to choose opponent to the particular Human.
     * @param human
     */
    private void makeHumanChooseOpponent(Human human) {
        try {
            human.chooseOpponent(this);
        } catch (SocketException e) {
            human.disconnectPlayer();
        }
    }

    /**
     * Delegates request to the particular Human to choose if he wants to play in new room or in existing one.
     * @param human
     */
    private void makeHumanDecideIfNewRoom(Human human) {
        try {
            human.decideIfNewRoom(this);
        } catch (SocketException e) {
            human.disconnectPlayer();
        }
    }

    /**
     * Delegates request to choose room to the particular Human.
     * @param human
     */
    private void makeHumanChooseRoom(Human human) {
        try {
            human.chooseRoom(this);
        } catch (SocketException e) {
            human.disconnectPlayer();
        }
    }

    /**
     * Assigns login to human.
     * @param human
     * @param login
     */
    @Override
    public void humanLogged(Human human, Login login) {
        human.setLogin(login);
    }

    /**
     * Takes different actions depending on the response of human about the type of his opponent.
     * @param human
     * @param response
     */
    @Override
    public void humanChoseOpponent(Human human, String response) {
        if(response.equals("BOT")){
            Bot bot = new Bot();
            Room room = new Room(human);
            rooms.add(room);
            room.setIndex(rooms.indexOf(room));
            room.setOpponent(bot);
        }
        else if(response.equals("HUMAN")){
            human.sendListOfRooms(listOfRoomsToStringList()); //sends the list of rooms to the particular human to show him it
            makeHumanDecideIfNewRoom(human);
        }

    }

    /**
     * Takes different actions depending on the response of human about the fact whether he want to play in new room or
     * in existing one.
     * @param human
     * @param response
     */
    @Override
    public void humanDecidedIfNewRoom(Human human, String response) {
        if(response.equals("NEW")){
            Room room = new Room(human);
            rooms.add(room);
            room.setIndex(rooms.indexOf(room));
            human.setIndexOfRoom(room.getIndex());
        }
        else if(response.equals("EXISTING")){
            makeHumanChooseRoom(human);
        }
    }

    /**
     * Called when human decicded to play in existing room, assigns him to this room.
     * @param human
     * @param numberOfRoom
     */
    @Override
    public void humanChoseRoom(Human human, int numberOfRoom) {
        Room chosenRoom = rooms.get(numberOfRoom);
        chosenRoom.setOpponent(human);
        human.setIndexOfRoom(chosenRoom.getIndex());
    }

    @Override
    public void humanExited(int indexOfRoom, Login login){
        rooms.get(indexOfRoom).deletePlayer(login);
    }

    /**
     * Parse the list of rooms
     * @return parsed list of rooms
     */
    public ArrayList<String> listOfRoomsToStringList(){
        ArrayList<String> stringListOfRooms = new ArrayList<>();
        for (Room room : rooms){
            stringListOfRooms.add(room.toString());
        }

        return stringListOfRooms;
    }
}
