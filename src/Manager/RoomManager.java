package Manager;

import Model.Room;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Room Manager class. Contains all the interactions with the database for Room objects.
 */
public class RoomManager extends Manager{

    public RoomManager(){ }

    /**
     * Get all rooms from the database.
     * @return The rooms list.
     */
    public List<Room> listAll(){
        return (List<Room>)(List<?>) super.sqlList("FROM Room ORDER BY number", null);
    }

    /**
     * Check if a room exists in the database.
     * @param number The room number.
     * @return The room if exists, otherwise null.
     */
    public Room exists(int number){
        String sql = "FROM Room WHERE number = :number";

        List<Pair<String, Object>> params = new ArrayList<>();
        params.add(new Pair<String, Object>("number", number));

        List list = super.sqlList(sql, params);

        if(list.size() != 0) return (Room) list.get(0);
        else return null;
    }
}
