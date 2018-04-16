package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Reservation model.
 */
@Entity
@Table(name = "Reservation")
public class Reservation extends RecursiveTreeObject<Reservation> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "reservation_date", nullable = false)
    private Date reservationDate;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date", nullable = false)
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "fk_employee")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "fk_client")
    private Client client;
    @ManyToMany
    @JoinTable(name = "reservation_room",
            joinColumns = { @JoinColumn(name = "fk_reservation") },
            inverseJoinColumns = { @JoinColumn(name = "fk_room") })
    private Set<Room> rooms;

    public Reservation() {}

    /**
     * Constructor.
     * @param reservationDate The reservation date.
     * @param startDate The start date.
     * @param endDate The end date.
     * @param employee The employee.
     * @param client The client.
     * @param rooms The rooms.
     */
    public Reservation(Date reservationDate, Date startDate, Date endDate, Employee employee, Client client, Set<Room> rooms) {
        this.reservationDate = reservationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
        this.client = client;
        this.rooms = rooms;
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
     * Get reservation date.
     * @return The reservation date.
     */
    public Date getReservationDate() { return reservationDate; }

    /**
     * Set reservation date.
     * @param reservationDate The reservation date.
     */
    public void setReservationDate(Date reservationDate) { this.reservationDate = reservationDate; }

    /**
     * Get start date.
     * @return The start date.
     */
    public Date getStartDate() { return startDate; }

    /**
     * Set start date.
     * @param startDate The start date.
     */
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    /**
     * Get end date.
     * @return The end date.
     */
    public Date getEndDate() { return endDate; }

    /**
     * Set end date.
     * @param endDate The end date.
     */
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    /**
     * Get employee.
     * @return The employee.
     */
    public Employee getEmployee() { return employee; }

    /**
     * Set employee.
     * @param employee The employee.
     */
    public void setEmployee(Employee employee) { this.employee = employee; }

    /**
     * Get client.
     * @return The client.
     */
    public Client getClient() { return client; }

    /**
     * Set client.
     * @param client The client.
     */
    public void setClient(Client client) { this.client = client; }

    /**
     * Get rooms.
     * @return The rooms list.
     */
    public Set<Room> getRooms() { return rooms; }

    /**
     * Set rooms.
     * @param rooms The rooms list.
     */
    public void setRooms(Set<Room> rooms) { this.rooms = rooms; }

    /**
     * Add room.
     * @param room The room.
     */
    public void addRoom(Room room) {
        this.rooms.add(room);
        room.getReservations().add(this);
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
        if (!(o instanceof Reservation))return false;
        if (this.id == ((Reservation) o).id) return true;
        else return false;
    }

}
