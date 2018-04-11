package Model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Reservation")
public class Reservation {

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
    @Column(name = "nb_guest", nullable = false)
    private int nbGuest;
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

    public Reservation(Date reservationDate, Date startDate, Date endDate, int nbGuest, Employee employee, Client client, Set<Room> rooms) {
        this.reservationDate = reservationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nbGuest = nbGuest;
        this.employee = employee;
        this.client = client;
        this.rooms = rooms;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Date getReservationDate() { return reservationDate; }

    public void setReservationDate(Date reservationDate) { this.reservationDate = reservationDate; }

    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public int getNbGuest() { return nbGuest; }

    public void setNbGuest(int nbGuest) { this.nbGuest = nbGuest; }

    public Employee getEmployee() { return employee; }

    public void setEmployee(Employee employee) { this.employee = employee; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }

    public Set<Room> getRooms() { return rooms; }

    public void setRooms(Set<Room> rooms) { this.rooms = rooms; }

    public void addRoom(Room room) {
        this.rooms.add(room);
        room.getReservations().add(this);
    }

}
