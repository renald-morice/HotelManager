package Model;

import javax.persistence.*;

@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "role", nullable = false)
    private String role;
    @Column(name = "access_level", nullable = false)
    private int accessLevel;

    public Role() {}

    public Role(String role, int accessLevel) {
        this.role = role;
        this.accessLevel = accessLevel;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public int getAccessLevel() { return accessLevel; }

    public void setAccessLevel(int accessLevel) { this.accessLevel = accessLevel; }

    public String toString() { return role+" "+accessLevel; }
}
