package Manager;

import Model.Client;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ClientManager extends Manager{

    public ClientManager(){ }

    public List<Client> listAll(){
        return (List<Client>)(List<?>) super.sqlList("FROM Client", null);
    }

    public Client exists(String firstName, String lastName, String phoneNumber){

        String sql = "FROM Client WHERE firstName = :firstName AND lastName = :lastName AND phoneNumber = :phoneNumber";

        List<Pair<String, Object>> params = new ArrayList<>();
        params.add(new Pair<String, Object>("firstName", firstName));
        params.add(new Pair<String, Object>("lastName", lastName));
        params.add(new Pair<String, Object>("phoneNumber", phoneNumber));

        List list = super.sqlList(sql, params);

        if(list.size() != 0) return (Client) list.get(0);
        else return null;

    }

}
