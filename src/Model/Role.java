package Model;

import javax.persistence.*;

/**
 * Role model.
 */
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

    /**
     * Constructor.
     * @param role The role.
     * @param accessLevel The access level.
     */
    public Role(String role, int accessLevel) {
        this.role = role;
        this.accessLevel = accessLevel;
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
     * Get role.
     * @return The role.
     */
    public String getRole() { return role; }

    /**
     * Set role.
     * @param role The role.
     */
    public void setRole(String role) { this.role = role; }

    /**
     * Get access level.
     * @return The access level.
     */
    public int getAccessLevel() { return accessLevel; }

    /**
     * Set access level.
     * @param accessLevel The access level.
     */
    public void setAccessLevel(int accessLevel) { this.accessLevel = accessLevel; }

    public String toString() { return role; }

    /**
     * Custom equals function.
     * @param o The object.
     * @return A boolean.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Role))return false;
        return this.id == ((Role) o).id;
    }
}
