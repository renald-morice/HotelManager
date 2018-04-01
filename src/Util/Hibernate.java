package Util;

import Model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Hibernate {

    private static Hibernate instance = null;
    public static SessionFactory sessionFactory;

    private Hibernate(){
        try {
            sessionFactory = new Configuration().configure()
                    .addAnnotatedClass(Employee.class)
                    .buildSessionFactory();

            Session session = Hibernate.sessionFactory.openSession();
            session.close();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Hibernate getInstance(){
        if(instance == null) instance = new Hibernate();
        return instance;
    }


}
