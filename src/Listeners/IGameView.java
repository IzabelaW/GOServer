package Listeners;

import Game.Human;
import Game.Room;

import java.io.IOException;
import java.util.List;

/**
 * Created by Izabela on 2016-12-01.
 */
public interface IGameView {

    void onCreateNewRoom(Room room) throws IOException;

    Room onJoinHumanToRoom(int numberOfRoom, Human human) throws IOException;

    List<String> getListOfIndexesToString();

    List<String> getListOfInitiatorsLoginsToString();

    List<String> getListOfJoinersLoginsToString();

    void onDeleteRoom(int indexOfRoom);
}