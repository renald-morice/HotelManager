package Manager;

import Model.Client;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Client Manager class. Contains all the interactions with the database for Client objects.
 */
public class ClientManager extends Manager{

    public ClientManager(){ }

    /**
     * Get all Clients from the database.
     * @return The clients list.
     */
    public List<Client> listAll(){
        return (List<Client>)(List<?>) super.sqlList("FROM Client ORDER BY first_name, last_name", null);
    }

    /**
     * Check if a client exists in the database.
     * @param firstName The first name.
     * @param lastName The last name.
     * @param phoneNumber The phone number.
     * @return The Client if exists, otherwise null.
     */
    public Client exists(String firstName, String lastName, String phoneNumber){
        String sql = "FROM Client WHERE first_name = :firstName AND last_name = :lastName AND phone_number = :phoneNumber";

        List<Pair<String, Object>> params = new ArrayList<>();
        params.add(new Pair<String, Object>("firstName", firstName));
        params.add(new Pair<String, Object>("lastName", lastName));
        params.add(new Pair<String, Object>("phoneNumber", phoneNumber));

        List list = super.sqlList(sql, params);

        if(list.size() != 0) return (Client) list.get(0);
        else return null;
    }
}
