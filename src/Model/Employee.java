package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Employee")
public class Employee extends RecursiveTreeObject<Employee> {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "salary", nullable = false)
    private double salary;
    @ManyToOne
    @JoinColumn(name = "fk_role")
    private Role role;
    @OneToMany(mappedBy = "employee")
    private List<Reservation> reservations;

    public Employee() {}

    public Employee(
            String username,
            String password,
            String firstName,
            String lastName,
            double salary,
            Role role
    ){
        this.username = username;
        this.password =  password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.role = role;
        this.reservations = new ArrayList<>();
    }

    public Employee(
            String username,
            String password,
            String firstName,
            String lastName,
            double salary,
            Role role,
            List<Reservation> reservations
    ){
        this.username = username;
        this.password =  password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.role = role;
        this.reservations = reservations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String first_name ) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String last_name ) {
        this.lastName = last_name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary( float salary ) {
        this.salary = salary;
    }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }

    public List<Reservation> getReservations() { return reservations; }

    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setEmployee(this);
    }
}