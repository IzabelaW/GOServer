package Game;

import Listeners.IHumanStartedGameListener;
import Listeners.IPlayerMadeTurnListener;
import com.google.gson.Gson;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Class which represents Human in Server.
 * Each Human is a single thread and implements interface IPlayer.
 */
public class Human extends Thread implements IPlayer {
    /**
     * Socket of Human.
     */
    private final Socket socket;

    /**
     * Output stream.
     */
    private PrintWriter out;

    /**
     * Input stream.
     */
    private BufferedReader in;

    /**
     * Login.
     */
    private Login login;

    /**
     * Index of room Human plays in.
     */
    private int indexOfRoom;

    /**
     * Response from a particular client.
     */
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

    /**
     * Sends to the particular client information about possibility of making turn,
     * then receives information about turn making by this client and delegates it
     * to the on of listeners of human
     * @param listener of making turns by human
     */
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

    /**
     * Receives login from the particular client and delegates it to the one of listeners of Human.
     * @param listener of initial part of game.
     */
    public synchronized void logIn(IHumanStartedGameListener listener) throws SocketException {
        try {
            String response = in.readLine();
            Login login = new Login(response);
            listener.humanLogged(this, login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Receives response from the particular client about type of opponent and delegates it to
     * the one of listeners of Human.
     * @param listener of initial part of game.
     */
    public synchronized void chooseOpponent(IHumanStartedGameListener listener) throws SocketException{
        try {
            String response = in.readLine();
            listener.humanChoseOpponent(this,response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Receives response from the particular client about whether he wants to play in new room or create new one
     * and delegates it to the one of listeners of Human.
     * @param listener of initial part of game.
     */
    public synchronized void decideIfNewRoom(IHumanStartedGameListener listener) throws SocketException{
        try {
            String response = in.readLine();
            listener.humanDecidedIfNewRoom(this,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Receives response from the particular client which involves information about existing room he chose
     * and delegates it to the one of listeners of Human.
     * @param listener of initial part of game.
     */
    public synchronized void chooseRoom(IHumanStartedGameListener listener) throws SocketException{
        try {
            String response = in.readLine();
            listener.humanChoseRoom(this, Integer.parseInt(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the login.
     * @param login
     */
    public void setLogin(Login login) {
        this.login = login;
    }

    /**
     * Gets the login.
     * @return login.
     */
    public Login getLogin() {
        return login;
    }

    /**
     * Sets the index of room Human plays in.
     * @param index of room.
     */
    public void setIndexOfRoom(int index){ indexOfRoom = index;}


    /**
     * Gets the index of room Human plays in.
     * @return index of room.
     */
    public int getIndexOfRoom(){ return indexOfRoom; }

    /**
     * Sends list of rooms to the particular client
     * @param rooms - list of rooms
     */
    public void sendListOfRooms(ArrayList<String> rooms){
        out.println(rooms);
    }

    /**
     * Delegates the necessary information about human who exited to the one of listeners of Human.
     * @param listener of initial part of game.
     */
    private void deletePlayer(IHumanStartedGameListener listener){
        listener.humanExited(indexOfRoom, login);
    }

    public void disconnectPlayer(){
        try {
            socket.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}