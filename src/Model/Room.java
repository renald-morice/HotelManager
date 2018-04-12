package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    public Room() {}

    public Room(int number, float price, int nbGuest) {
        this.number = number;
        this.price = price;
        this.nbGuest = nbGuest;
        this.reservations = new HashSet<>();
    }

    public Room(int number, float price, int nbGuest, Set<Reservation> reservations) {
        this.number = number;
        this.price = price;
        this.nbGuest = nbGuest;
        this.reservations = reservations;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public float getPrice() { return price; }

    public void setPrice(float price) { this.price = price; }

    public int getNbGuest() { return nbGuest; }

    public void setNbGuest(int nbGuest) { this.nbGuest = nbGuest; }

    public Set<Reservation> getReservations() { return reservations; }

    public void setReservations(Set<Reservation> reservations) { this.reservations = reservations; }

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

}
