package Game;

import Listeners.IHumanStartedGameListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by Kasia on 2016-11-29.
 */
public class Game implements IHumanStartedGameListener {
    private ArrayList<Room> rooms;

    public Game() {
        rooms = new ArrayList<Room>();

    }

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

    private void makeHumanLogIn(Human human){
        human.logIn(this);
    }

    private void makeHumanChooseOpponent(Human human){
        human.chooseOpponent(this);
    }

    private void makeHumanDecideIfNewRoom(Human human){
        human.decideIfNewRoom(this);
    }

    private void makeHumanChooseRoom(Human human){
        human.chooseRoom(this);
    }

    @Override
    public void humanLogged(Human human, Login login) {
        human.setLogin(login);
    }

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
            human.sendListOfRooms(listOfRoomsToStringList());
            makeHumanDecideIfNewRoom(human);
        }

    }

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

    public ArrayList<String> listOfRoomsToStringList(){
        ArrayList<String> stringListOfRooms = new ArrayList<>();
        for (Room room : rooms){
            stringListOfRooms.add(room.toString());
        }

        return stringListOfRooms;
    }
}
