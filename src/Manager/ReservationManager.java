package Manager;

import Model.Reservation;
import java.util.List;


public class ReservationManager extends Manager{

    public ReservationManager(){ }

    public List<Reservation> listAll(){
        return (List<Reservation>)(List<?>) super.sqlList("FROM Reservation ORDER BY start_date, end_date", null);
    }

}
