package Game;

import Listeners.PlayerMadeTurnListener;
import Listeners.PlayerStartedGameListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Kasia on 2016-11-29.
 */
public class Human extends Thread implements IPlayer {
    private final Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Human(Socket socket){
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.println("WAITING FOR OPPONENT");

    }

    @Override
    public void makeTurn(PlayerMadeTurnListener listener){
        Gson gson = new Gson();

        try{
            out.println("MAKE_TURN");

            String response = in.readLine();

            Turn turn = gson.fromJson(response, Turn.class);
            listener.playerMadeTurn(this, turn);

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void logIn(PlayerStartedGameListener listener){
        Gson gson = new Gson();

        try {

            String response = in.readLine();
            Login login = gson.fromJson(response, Login.class);
            listener.playerLogged(this, login);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void chooseOpponent(PlayerStartedGameListener listener){

        try {

            String response = in.readLine();
            listener.playerChoseOpponent(this,response);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void decideIfNewRoom(PlayerStartedGameListener listener){

        try {

            String response = in.readLine();
            listener.playerDecidedIfNewRoom(this,response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chooseRoom(PlayerStartedGameListener listener){

        try {

            String response = in.readLine();
            listener.playerChoseRoom(this, Integer.parseInt(response));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}