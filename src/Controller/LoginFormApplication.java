package Controller;

import Model.Employee;
import Util.Constants;
import Util.Hibernate;
import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginFormApplication extends Application {

    private Stage window;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception  {
        //Hibernate initialization
        Hibernate.init();

        Parent root = FXMLLoader.load(getClass().getResource(Constants.LOGIN_FXML));
        window = primaryStage;
        JFXDecorator decorator = new JFXDecorator(window,root);
        decorator.setText(Constants.WINDOW_TITLE);
        window.setScene(new Scene(decorator));
        window.show();

//        /*-------------*/
//        /* TEST MODELS */
//        /*-------------*/
//
//        // 1 Employee + Role
//        //------------------
//        EmployeeManager employeeManager = new EmployeeManager();
//        RoleManager roleManager = new RoleManager();
//        Role ceoRole = new Role("CEO", 10);
//
//        Employee employee = new Employee(
//                "Momo",
//                MD5Hashing.hash("test"),
//                "Rénald",
//                "Morice",
//                5000.1,
//                ceoRole
//        );
//
//        roleManager.add(ceoRole);
//        employeeManager.add(employee);
//
//        // 1 Client
//        //---------
//        ClientManager clientManager = new ClientManager();
//        Client client = new Client("Toto", "Tata", "5814901880", "tototata@gmail.com");
//
//        clientManager.add(client);
//
//        // 2 Rooms
//        //--------
//        RoomManager roomManager = new RoomManager();
//        Room room = new Room(100, 80, 4, null);
//        Room room2 = new Room(200, 180, 2, null);
//        roomManager.add(room);
//        roomManager.add(room2);
//
//        // 1 Reservation
//        //--------------
//        ReservationManager reservationManager = new ReservationManager();
//        Set<Room> rooms = new HashSet<>();
//        rooms.add(room);
//        rooms.add(room2);
//        Reservation reservation = new Reservation(new Date(), new Date(), new Date(), 5, employee, client, rooms);
//
//        reservationManager.add(reservation);

    }
}
