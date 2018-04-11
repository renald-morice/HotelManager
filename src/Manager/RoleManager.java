package Manager;

import Model.Role;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;


public class RoleManager extends Manager{

    public RoleManager(){ }

    public List<Role> listAll(){
        return (List<Role>)(List<?>) super.sqlList("FROM Role", null);
    }

    public Role exists(String role){

        String sql = "FROM Role WHERE role = :role";

        List<Pair<String, Object>> params = new ArrayList<>();
        params.add(new Pair<String, Object>("role", role));

        List list = super.sqlList(sql, params);

        if(list.size() != 0) return (Role) list.get(0);
        else return null;

    }

}