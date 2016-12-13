package Listeners;

import Game.Human;
import Game.Room;

import java.util.List;

/**
 * Created by Izabela on 2016-12-01.
 */
public interface IGameView {

    void onCreateNewRoom(Room room);

    Room onJoinHumanToRoom(int numberOfRoom, Human human);

    List<String> getListOfIndexesToString();

    List<String> getListOfInitiatorsLoginsToString();

    List<String> getListOfJoinersLoginsToString();

}