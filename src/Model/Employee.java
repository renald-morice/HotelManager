package Model;

import javax.persistence.*;

@Entity
@Table(name = "Employee")
public class Employee{

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "salary")
    private float salary;

    public Employee() {}

    public Employee(String username, String password, String fname, String lname, float salary ) {
        this.username = username;
        this.password = password;
        this.firstName = fname;
        this.lastName = lname;
        this.salary = salary;
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

    public float getSalary() {
        return salary;
    }

    public void setSalary( float salary ) {
        this.salary = salary;
    }
}