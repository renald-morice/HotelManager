package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Client Model.
 */
@Entity
@Table(name = "Client")
public class Client extends RecursiveTreeObject<Client> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "email", nullable = false)
    private String email;
    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;

    public Client() {}

    /**
     * Constructor.
     * @param firstName The first name.
     * @param lastName The last name.
     * @param phoneNumber The phone number.
     * @param email The email.
     */
    public Client(
            String firstName,
            String lastName,
            String phoneNumber,
            String email
    ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.reservations = new ArrayList<>();
    }

    /**
     * Constructor.
     * @param firstName The first name.
     * @param lastName The last name.
     * @param phoneNumber The phone number.
     * @param email The email.
     * @param reservations The reservations list.
     */
    public Client(
            String firstName,
            String lastName,
            String phoneNumber,
            String email,
            List<Reservation> reservations
    ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.reservations = reservations;
    }

    /**
     * Get Id.
     * @return The id.
     */
    public int getId() { return id; }

    /**
     * Set Id.
     * @param id The id.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Get first name.
     * @return The first name.
     */
    public String getFirstName() { return firstName; }

    /**
     * Set first name.
     * @param firstName The first name.
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     * Get last name.
     * @return The last name.
     */
    public String getLastName() { return lastName; }

    /**
     * Set last name.
     * @param lastName The last name.
     */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /**
     * Get phone number.
     * @return The phone number.
     */
    public String getPhoneNumber() { return phoneNumber; }

    /**
     * Set phone number.
     * @param phoneNumber The phone number.
     */
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    /**
     * Get email.
     * @return The email.
     */
    public String getEmail() { return email; }

    /**
     * Set email.
     * @param email The email.
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Get reservations.
     * @return The reservations list..
     */
    public List<Reservation> getReservations() { return reservations; }

    /**
     * Set reservations.
     * @param reservations The reservations list.
     */
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    /**
     * Add a reservation.
     * @param reservation The reservation.
     */
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setClient(this);
    }

    public String toString() { return firstName + " " + lastName; }

    /**
     * Custom equals function.
     * @param o The object.
     * @return A boolean.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Client))return false;
        if (this.id == ((Client) o).id) return true;
        else return false;
    }

}
