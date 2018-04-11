package Manager;

import Util.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public abstract class Manager {

    public void add(Object o){
        Session session = Hibernate.sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(o);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public void update(Object o){
        Session session = Hibernate.sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    protected List<Object> list(String table){
        Session session = Hibernate.sessionFactory.openSession();
        Transaction transaction = null;
        List<Object> objects = null;

        try {
            transaction = session.beginTransaction();
            objects = (List<Object>) session.createQuery("from " + table).list();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return objects;
    }

}
