package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Employee model.
 */
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

    /**
     * Constructor.
     * @param username The username.
     * @param password The password.
     * @param firstName The first name.
     * @param lastName The last name.
     * @param salary The salary.
     * @param role The role.
     */
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

    /**
     * Constructor.
     * @param username The username.
     * @param password The password.
     * @param firstName The first name.
     * @param lastName The last name.
     * @param salary The salary.
     * @param role The role.
     * @param reservations The reservations.
     */
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

    /**
     * Get id.
     * @return The id.
     */
    public int getId() {
        return id;
    }

    /**
     * Set id.
     * @param id The id.
     */
    public void setId( int id ) {
        this.id = id;
    }

    /**
     * Get username.
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username
     * @param username The username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password.
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password.
     * @param password The password.
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Get first name.
     * @return The first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set first name.
     * @param first_name The first name.
     */
    public void setFirstName( String first_name ) {
        this.firstName = first_name;
    }

    /**
     * Get last name.
     * @return The last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set last name.
     * @param last_name The last name.
     */
    public void setLastName( String last_name ) {
        this.lastName = last_name;
    }

    /**
     * Get salary.
     * @return The salary.
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Set salary.
     * @param salary The salary.
     */
    public void setSalary( double salary ) {
        this.salary = salary;
    }

    /**
     * Get role.
     * @return The role.
     */
    public Role getRole() { return role; }

    /**
     * Set role.
     * @param role The role.
     */
    public void setRole(Role role) { this.role = role; }

    /**
     * Get the reservations.
     * @return The reservations list.
     */
    public List<Reservation> getReservations() { return reservations; }

    /**
     * Set the reservations.
     * @param reservations The reservations list.
     */
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    /**
     * Add reservation.
     * @param reservation The reservation.
     */
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setEmployee(this);
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
        if (!(o instanceof Employee))return false;
        return this.id == ((Employee) o).id;
    }
}