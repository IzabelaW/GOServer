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
            room.setJoiner(bot);
        }
        else if(response.equals("HUMAN")){
            human.sendLists(listOfIndexesToString());
            human.sendLists(listOfInitiatorsLoginsToString());
            human.sendLists(listOfJoinersLoginsToString());//sends the list of rooms to the particular human to show him it
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
            human.setPlayerColor(PlayerColor.WHITE);
        }
        else if(response.equals("EXISTING")){
            makeHumanChooseRoom(human);
        }
    }

    /**
     * Called when human decided to play in existing room, assigns him to this room.
     * @param human
     * @param numberOfRoom
     */
    @Override
    public void humanChoseRoom(Human human, int numberOfRoom) {
        Room chosenRoom = rooms.get(numberOfRoom);
        chosenRoom.setJoiner(human);
        human.setIndexOfRoom(chosenRoom.getIndex());
        human.setPlayerColor(PlayerColor.BLACK);
        chosenRoom.turnForPlayer(human);
        human.setOpponent(chosenRoom.getInitiator());
        chosenRoom.getInitiator().setOpponent(human);
    }

    /**
     * Parse the list of indexes to String.
     * @return parsed list of indexes.
     */
    public ArrayList<String> listOfIndexesToString(){
        ArrayList<String> stringListOfIndexes = new ArrayList<>();
        for (Room room : rooms){
            stringListOfIndexes.add(Integer.toString(room.getIndex()));
        }

        return stringListOfIndexes;
    }

    /**
     * Parse the list of initiators logins to String.
     * @return parsed list of initiators' logins.
     */
    public ArrayList<String> listOfInitiatorsLoginsToString(){
        ArrayList<String> stringListOfPlayer1Logins = new ArrayList<>();
        for (Room room : rooms){
            stringListOfPlayer1Logins.add(room.getInitiator().getLogin().toString());
        }

        return stringListOfPlayer1Logins;
    }

    /**
     * Parse the list of joiners logins to String.
     * @return parsed list of joiners' logins.
     */
    public ArrayList<String> listOfJoinersLoginsToString(){
        ArrayList<String> stringListOfPlayer2Logins = new ArrayList<>();
        for (Room room: rooms){
            stringListOfPlayer2Logins.add(room.getJoiner().getLogin().toString());
        }

        return stringListOfPlayer2Logins;
    }
}
