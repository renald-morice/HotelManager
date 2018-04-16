package Manager;

import Model.Role;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Role Manager class. Contains all the interactions with the database for Role objects.
 */
public class RoleManager extends Manager{

    public RoleManager(){ }

    /**
     * Get all roles from the database.
     * @return The Role list.
     */
    public List<Role> listAll(){
        return (List<Role>)(List<?>) super.sqlList("FROM Role ORDER BY access_level", null);
    }

    /**
     * Check if a role exists in the database.
     * @param role The role.
     * @return The role if exists, otherwise null.
     */
    public Role exists(String role){
        String sql = "FROM Role WHERE role = :role";

        List<Pair<String, Object>> params = new ArrayList<>();
        params.add(new Pair<String, Object>("role", role));

        List list = super.sqlList(sql, params);

        if(list.size() != 0) return (Role) list.get(0);
        else return null;
    }
}