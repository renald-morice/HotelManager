package Manager;

import Model.Reservation;
import java.util.List;

/**
 * Reservation Manager class. Contains all the interactions with the database for Reservation objects.
 */
public class ReservationManager extends Manager{

    public ReservationManager(){ }

    /**
     * Get all Reservations from the database.
     * @return The Reservation list.
     */
    public List<Reservation> listAll(){
        return (List<Reservation>)(List<?>) super.sqlList("FROM Reservation ORDER BY start_date, end_date", null);
    }
}
