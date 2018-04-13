package Manager;

import Model.Room;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;


public class RoomManager extends Manager{

    public RoomManager(){ }

    public List<Room> listAll(){
        return (List<Room>)(List<?>) super.sqlList("FROM Room ORDER BY number", null);
    }

    public Room exists(int number){

        String sql = "FROM Room WHERE number = :number";

        List<Pair<String, Object>> params = new ArrayList<>();
        params.add(new Pair<String, Object>("number", number));

        List list = super.sqlList(sql, params);

        if(list.size() != 0) return (Room) list.get(0);
        else return null;

    }

}
