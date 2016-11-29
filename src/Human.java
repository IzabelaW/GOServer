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
    BufferedReader in;

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
    public void makeTurn(OnPlayerMadeTurnListener listener){
        Gson gson = new Gson();

        try{
            out.println("MAKE_TURN");

            String response = in.readLine();

            Turn turn = gson.fromJson(response, Turn.class);
            listener.onPlayerMadeTurn(this, turn);

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public boolean  askIfBot() throws IOException{
        out.println("IFBOT");
        String answer = in.readLine();
        if(answer.equals("YES"))
            return true;
        else
            return false;


    }




}