package Manager;

import Model.Employee;
import Util.Hibernate;
import Util.MD5Hashing;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;

public class EmployeeManager {

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

    public int add(String login, String password, String firstName, String lastName, int salary){
        Session session = Hibernate.sessionFactory.openSession();
        Transaction transaction = null;
        Integer employeeID = null;

        try {
            transaction = session.beginTransaction();
            Employee employee = new Employee(login, MD5Hashing.hash(password), firstName, lastName, salary);
            employeeID = (Integer) session.save(employee);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeID;
    }

    public void list(){
        Session session = Hibernate.sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            List<Employee> result = (List<Employee>) session.createQuery("from Employee").list();
            for (Iterator iterator = result.iterator(); iterator.hasNext();){
                Employee employee = (Employee) iterator.next();
                System.out.print("First Name: " + employee.getFirstName());
                System.out.print("  Last Name: " + employee.getLastName());
                System.out.println("  Salary: " + employee.getSalary());
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateSalary(Integer EmployeeID, int salary ){
        Session session = Hibernate.sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee)session.get(Employee.class, EmployeeID);
            employee.setSalary( salary );
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void delete(Integer EmployeeID){
        Session session = Hibernate.sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Employee employee = (Employee)session.get(Employee.class, EmployeeID);
            session.delete(employee);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}