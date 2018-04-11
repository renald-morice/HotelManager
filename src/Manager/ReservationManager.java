package Manager;

import Model.Reservation;
import java.util.List;


public class ReservationManager extends Manager{

    public ReservationManager(){ }

    public List<Reservation> list(){
        return (List<Reservation>)(List<?>) super.list("Reservation");
    }

}
