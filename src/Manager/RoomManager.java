package Manager;

import Model.Room;
import java.util.List;


public class RoomManager extends Manager{

    public RoomManager(){ }

    public List<Room> list(){
        return (List<Room>)(List<?>) super.list("Room");
    }

}
