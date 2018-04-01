package Util;

import Model.Employee;
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
                        .buildSessionFactory();
            } catch (Throwable ex) {
                System.err.println("Failed to create sessionFactory object." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
    }

}
