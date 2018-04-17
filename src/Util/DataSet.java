package Util;

import Manager.*;
import Model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DataSet class.
 */
public class DataSet {

    /**
     * Load a default dataset to the database.
     * @throws ParseException Any exception
     */
    public static void data() throws ParseException {

        //------------------
        // Roles
        //------------------

        RoleManager roleManager = new RoleManager();

        Role receptionistRole = new Role("receptionist", 1);
        Role managerRole = new Role("manager", 2);
        Role ceoRole = new Role("ceo", 3);

        roleManager.add(receptionistRole);
        roleManager.add(managerRole);
        roleManager.add(ceoRole);

        //------------------
        // Employees
        //------------------

        EmployeeManager employeeManager = new EmployeeManager();

        Employee ceoEmp = new Employee(
                "ceo",
                MD5Hashing.hash("test"),
                "Max",
                "Parsons",
                5000,
                ceoRole
        );

        Employee managerEmp = new Employee(
                "manager",
                MD5Hashing.hash("test"),
                "Jennifer",
                "Arnold",
                4000,
                managerRole
        );

        Employee receptionistEmp = new Employee(
                "receptionist",
                MD5Hashing.hash("test"),
                "Earnest",
                "Holmes",
                3000,
                receptionistRole
        );

        employeeManager.add(receptionistEmp);
        employeeManager.add(managerEmp);
        employeeManager.add(ceoEmp);

        //------------------
        // Clients
        //------------------

        ClientManager clientManager = new ClientManager();

        Client client1 = new Client(
                "Kristine",
                "Reed",
                "613-555-0178",
                "kristine.reed@gmail.com"
        );

        Client client2 = new Client(
                "Taylor",
                "George",
                "613-555-0175",
                "taylor.george@gmail.com"
        );

        Client client3 = new Client(
                "Doug",
                "Robinson",
                "613-555-0182",
                "doug.robinson@gmail.com"
        );

        clientManager.add(client1);
        clientManager.add(client2);
        clientManager.add(client3);

        //------------------
        // Rooms
        //------------------

        RoomManager roomManager = new RoomManager();

        Room room1 = new Room(100, 80, 4, null);
        Room room2 = new Room(200, 180, 4, null);
        Room room3 = new Room(300, 280, 3, null);
        Room room4 = new Room(400, 380, 2, null);

        roomManager.add(room1);
        roomManager.add(room2);
        roomManager.add(room3);
        roomManager.add(room4);

        //------------------
        // Reservations
        //------------------

        ReservationManager reservationManager = new ReservationManager();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");

        Set<Room> rooms1 = new HashSet<>();
        rooms1.add(room1);
        Reservation reservation1 = new Reservation(
                new Date(),
                dateFormat.parse("2018-04-7"),
                dateFormat.parse("2018-04-8"),
                receptionistEmp,
                client1,
                rooms1
        );

        Set<Room> rooms2 = new HashSet<>();
        rooms2.add(room2);
        rooms2.add(room3);
        Reservation reservation2 = new Reservation(
                new Date(),
                dateFormat.parse("2018-04-11"),
                dateFormat.parse("2018-04-12"),
                receptionistEmp,
                client2,
                rooms2
        );

        Set<Room> rooms3 = new HashSet<>();
        rooms3.add(room3);
        rooms3.add(room4);
        Reservation reservation3 = new Reservation(
                new Date(),
                dateFormat.parse("2018-04-14"),
                dateFormat.parse("2018-04-15"),
                managerEmp,
                client3,
                rooms3
        );

        reservationManager.add(reservation1);
        reservationManager.add(reservation2);
        reservationManager.add(reservation3);

    }

}
