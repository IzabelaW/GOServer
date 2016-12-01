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

    public void startGame() {
        try {
            ServerSocket listener = new ServerSocket(8900);

            while (true) {
                Human player1 = new Human(listener.accept());
                makeHumanLogIn(player1);
                makeHumanChooseOpponent(player1);
            }

        } catch (IOException e) {
            e.printStackTrace();
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
            makeHumanDecideIfNewRoom(human);
        }

    }

    @Override
    public void humanDecidedIfNewRoom(Human human, String response) {
        if(response.equals("NEW")){
            Room room = new Room(human);
            rooms.add(room);
            room.setIndex(rooms.indexOf(room));
        }
        else if(response.equals("EXISTING")){
            makeHumanChooseRoom(human);
        }
    }

    @Override
    public void humanChoseRoom(Human human, int numberOfRoom) {
        Room chosenRoom = rooms.get(numberOfRoom);
        chosenRoom.setOpponent(human);
    }
}
