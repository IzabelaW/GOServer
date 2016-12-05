package Game;

import Listeners.IHumanStartedGameListener;
import Listeners.IPlayerMadeTurnListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by Kasia on 2016-11-29.
 */
public class Human extends Thread implements IPlayer {
    private final Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Login login;
    private int indexOfRoom;
    private String response;

    public Human(Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void makeTurn(IPlayerMadeTurnListener listener){
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

    public synchronized void logIn(IHumanStartedGameListener listener){
        if (!ifExit()) {
            System.out.println(response);
            Login login = new Login(response);
            listener.humanLogged(this, login);

        } else {
            disconnectPlayer();
        }

    }

    public synchronized void chooseOpponent(IHumanStartedGameListener listener){

        if (!ifExit()) {
            listener.humanChoseOpponent(this,response);
        }
        else {
            disconnectPlayer();
        }

    }

    public synchronized void decideIfNewRoom(IHumanStartedGameListener listener){

        if (!ifExit()) {
            listener.humanDecidedIfNewRoom(this,response);
        }
        else {
            disconnectPlayer();
        }
    }

    public synchronized void chooseRoom(IHumanStartedGameListener listener){

        if (!ifExit()) {
            listener.humanChoseRoom(this, Integer.parseInt(response));
        }
        else {
            disconnectPlayer();
        }

    }


    public void setLogin(Login login) {
        this.login = login;
    }

    public Login getLogin() {
        return login;
    }

    public void setIndexOfRoom(int index){ indexOfRoom = index;}

    public int getIndexOfRoom(){ return indexOfRoom; }

    public void sendListOfRooms(ArrayList<String> rooms){
        out.println(rooms);
    }

    private boolean ifExit() {
        try {
            response = in.readLine();
            if (response.equals("EXIT")){
                return true;
            }
            else{
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void deletePlayer(IHumanStartedGameListener listener){
        listener.humanExited(indexOfRoom, login);
    }

    private void disconnectPlayer(){
        try {
            socket.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }




}