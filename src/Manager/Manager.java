package Manager;

import Util.Hibernate;
import javafx.util.Pair;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Manager class. Contains all the methods used by the managers which extends this class.
 */
public abstract class Manager {

    /**
     * Add an object to the database.
     * @param o The object.
     */
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

    /**
     * Update an object to the database.
     * @param o The object.
     */
    public void update(Object o){
        Session session = Hibernate.sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.update(o);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Delete an object to the database.
     * @param o The object.
     */
    public void delete(Object o){
        Session session = Hibernate.sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.delete(o);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Perform a custom SQL query.
     * @param sql The sql query string.
     * @param params All the sql query parameters.
     * @return THe results of the given query.
     */
    protected List<Object> sqlList(String sql, List<Pair<String, Object>> params){

        Session session = Hibernate.sessionFactory.openSession();
        Transaction transaction = null;
        List<Object> objects = null;

        Query query = session.createQuery(sql);
        if(params != null){
            for(Pair<String, Object> param : params) query.setParameter(param.getKey(), param.getValue());
        }

        try {
            transaction = session.beginTransaction();
            objects = query.list();
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
