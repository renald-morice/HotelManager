package Util;

import Model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Hibernate {

    public static SessionFactory sessionFactory;
    private static boolean isInitialized = false;

    public static void init(){

        if(!isInitialized){
            isInitialized = true;

            try {
                sessionFactory = new Configuration().configure()
                        .addAnnotatedClass(Employee.class)
                        .addAnnotatedClass(Client.class)
                        .addAnnotatedClass(Reservation.class)
                        .addAnnotatedClass(Role.class)
                        .addAnnotatedClass(Room.class)
                        .buildSessionFactory();
            } catch (Throwable ex) {
                System.err.println(Constants.HIBERNATE_SESSIONFACTORY_FAIL + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
    }

}
