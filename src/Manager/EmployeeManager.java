package Manager;

import Model.Employee;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager extends Manager{

    public EmployeeManager(){ }

    public List<Employee> listAll(){
        return (List<Employee>)(List<?>) super.sqlList("FROM Employee ORDER BY first_name, last_name", null);
    }

    public Employee exists(String username){

        String sql = "FROM Employee WHERE username = :username";

        List<Pair<String, Object>> params = new ArrayList<>();
        params.add(new Pair<String, Object>("username", username));

        List list = super.sqlList(sql, params);

        if(list.size() != 0) return (Employee) list.get(0);
        else return null;

    }

}