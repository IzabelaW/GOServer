package Game;

import Listeners.IHumanStartedGameListener;
import Listeners.IPlayerMadeGameDecisionListener;

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
     * Color of player's stones.
     */
    private PlayerColor playerColor;

    /**
     * Opponent of the player.
     */
    private IPlayer opponent;

    /**
     * Statement if one of players passed.
     */
    public boolean ifOpponentPassed = false;


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
    public synchronized void makeGameDecision(IPlayerMadeGameDecisionListener listener){

        try{
            out.println("MAKE_TURN");

            String response = in.readLine();

            if(response.startsWith("TURN")) {

                String[] move = response.split(" ");
                Turn turn = new Turn(Integer.parseInt(move[1]), Integer.parseInt(move[2]), playerColor);

                listener.playerMadeTurn(this, turn);

            } else if (response.equals("PASS")){

                if(ifOpponentPassed){
                       listener.playersPassed();
                }
                else {
                    opponent.opponentPassed();
                    opponent.makeGameDecision(listener);
                    return;
                }
            }
            else if (response.equals("WANNA_GIVE_UP")){
                opponent.opponentGaveUp();
                disconnectPlayer();
            }

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
     * Sets color of player's stones.
     * @param playerColor - color of player's stones.
     */
    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * Gets color of player's stones.
     * @return color of player's stones.
     */
    public PlayerColor getPlayerColor(){
        return playerColor;
    }

    /**
     * Sets opponent of the player.
     * @param opponent - opponent of the player.
     */
    public void setOpponent(IPlayer opponent) {
        this.opponent = opponent;
    }

    /**
     * Gets opponent of the player.
     * @return opponent of the player.
     */
    public IPlayer getOpponent() {
        return opponent;
    }

    /**
     * Sends list to the particular client.
     * @param list - list.
     */
    public void sendLists(ArrayList<String> list){
        out.println(list);
    }

    /**
     * Sends table of updated fields on the board.
     * @param updatedBoard - updated board.
     */
    public void sendUpdatedBoard(ArrayList<String> updatedBoard) { out.println("UPDATED_BOARD " + updatedBoard);}

    /**
     * Sends message to player when his opponent has just passed.
     */
    public void opponentPassed() {
        ifOpponentPassed=true;
        out.println("OPPONENT_PASSED");
    }

    /**
     * Sends message to player when his opponent has just gave up.
     */
    public void opponentGaveUp(){
        out.println("OPPONENT_GAVE_UP");
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