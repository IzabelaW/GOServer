import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by Kasia on 2016-11-29.
 */
public class Game {
    ArrayList<Room> rooms;


    public Game() {
        rooms = new ArrayList<Room>();

    }

    public void startGame() {
        try {
            ServerSocket listener = new ServerSocket(8900);

            while (true) {

                Human player1 = new Human(listener.accept());
                if(player1.askIfBot()) {
                    Human player2 = new Human(listener.accept());
                    Room room = new Room(player1, player2);
                    rooms.add(room);
                    player1.start();
                    player2.start();

                } else {
                    Bot player2 = new Bot();
                    Room room = new Room(player1, player2);
                    rooms.add(room);
                    player1.start();
                    player2.start();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
