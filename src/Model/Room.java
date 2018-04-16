package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Room model.
 */
@Entity
@Table(name = "Room")
public class Room extends RecursiveTreeObject<Room> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "number", nullable = false)
    private int number;
    @Column(name = "price", nullable = false)
    private float price;
    @Column(name = "nb_guest", nullable = false)
    private int nbGuest;
    @ManyToMany(mappedBy="rooms", fetch = FetchType.EAGER)
    private Set<Reservation> reservations;

    //Only use when creating a reservation
    @Transient
    private final BooleanProperty selectedProperty = new SimpleBooleanProperty();

    public Room() {}

    /**
     * Constructor.
     * @param number The number.
     * @param price The price.
     * @param nbGuest  The guests number.
     */
    public Room(int number, float price, int nbGuest) {
        this.number = number;
        this.price = price;
        this.nbGuest = nbGuest;
        this.reservations = new HashSet<>();
    }

    /**
     * Constructor.
     * @param number The number.
     * @param price The price.
     * @param nbGuest The guests number.
     * @param reservations The reservations list.
     */
    public Room(int number, float price, int nbGuest, Set<Reservation> reservations) {
        this.number = number;
        this.price = price;
        this.nbGuest = nbGuest;
        this.reservations = reservations;
    }

    /**
     * Get id.
     * @return The id.
     */
    public int getId() { return id; }

    /**
     * Set id.
     * @param id The id.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Get number.
     * @return The number.
     */
    public int getNumber() { return number; }

    /**
     * Set number.
     * @param number The number.
     */
    public void setNumber(int number) { this.number = number; }

    /**
     * Get price.
     * @return The price.
     */
    public float getPrice() { return price; }

    /**
     * Set price.
     * @param price The price.
     */
    public void setPrice(float price) { this.price = price; }

    /**
     * Get guests number.
     * @return The guests number.
     */
    public int getNbGuest() { return nbGuest; }

    /**
     * Set guests number.
     * @param nbGuest The guests number.
     */
    public void setNbGuest(int nbGuest) { this.nbGuest = nbGuest; }

    /**
     * Get reservations list.
     * @return The reservations list.
     */
    public Set<Reservation> getReservations() { return reservations; }

    /**
     * Set reservations.
     * @param reservations The reservations list.
     */
    public void setReservations(Set<Reservation> reservations) { this.reservations = reservations; }

    /**
     * Get selected property.
     * @return A BooleanProperty.
     */
    public BooleanProperty getSelectedProperty() {
        return this.selectedProperty;
    }

    /**
     * Get selected.
     * @return The selected room.
     */
    public final boolean getSelected() {
        return this.selectedProperty.get();
    }

    /**
     * Set selected property.
     * @param active A boolean.
     */
    public final void setselectedProperty(boolean active) {
        this.selectedProperty.set(active);
    }

    /**
     * Check if a room is available in a given period.
     * @param startDate The start date.
     * @param endDate The end date.
     * @return A boolean.
     */
    public boolean isAvailable(Date startDate, Date endDate){
        if(reservations != null){
            for(Reservation reservation : reservations){
                if( startDate.compareTo(reservation.getStartDate()) * reservation.getStartDate().compareTo(endDate) >= 0
                        || startDate.compareTo(reservation.getEndDate()) * reservation.getEndDate().compareTo(endDate) >= 0)
                    return false;
            }
        }
        return true;
    }

    /**
     * Custom equals function.
     * @param o The object.
     * @return A boolean.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Room))return false;
        return this.id == ((Room) o).id;
    }

}
