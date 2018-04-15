package Manager;

import Util.Hibernate;
import javafx.util.Pair;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
