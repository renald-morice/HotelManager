package Manager;

import Model.Role;
import java.util.List;


public class RoleManager extends Manager{

    public RoleManager(){ }

    public List<Role> list(){
        return (List<Role>)(List<?>) super.list("Role");
    }

}