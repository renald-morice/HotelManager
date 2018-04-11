package Manager;

import Model.Employee;
import Util.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class EmployeeManager extends Manager{

    public EmployeeManager(){ }

    public Employee login(String username, String password){
        Session session = Hibernate.sessionFactory.openSession();
        Transaction transaction = null;
        Employee employee = null;

        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Employee WHERE username = :username AND password = :password");
            query.setParameter("username", username);
            query.setParameter("password", password);

            List list = query.list();
            if(list.size() != 0) employee = (Employee) list.get(0);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return employee;
    }

    public List<Employee> list(){
        return (List<Employee>)(List<?>) super.list("Employee");
    }

}