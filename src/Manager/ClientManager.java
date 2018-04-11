package Manager;

import Model.Client;
import java.util.List;

public class ClientManager extends Manager{

    public ClientManager(){ }

    public List<Client> list(){
        return (List<Client>)(List<?>) super.list("Client");
    }

}
