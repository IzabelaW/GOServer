package Game;

import Listeners.IPlayerMadeTurnListener;
import Listeners.IHumanStartedGameListener;
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
    private Login login;

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
    public void makeTurn(IPlayerMadeTurnListener listener){
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

    public void logIn(IHumanStartedGameListener listener){
        Gson gson = new Gson();

        try {

            String response = in.readLine();
            Login login = gson.fromJson(response, Login.class);
            listener.humanLogged(this, login);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void chooseOpponent(IHumanStartedGameListener listener){

        try {

            String response = in.readLine();
            listener.humanChoseOpponent(this,response);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void decideIfNewRoom(IHumanStartedGameListener listener){

        try {

            String response = in.readLine();
            listener.humanDecidedIfNewRoom(this,response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chooseRoom(IHumanStartedGameListener listener){

        try {

            String response = in.readLine();
            listener.humanChoseRoom(this, Integer.parseInt(response));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void setLogin(Login login) {
        this.login = login;
    }

    public Login getLogin() {
        return login;
    }


}