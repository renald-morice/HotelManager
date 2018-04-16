package Controller;

import Model.Employee;

/**
 * Session class. Contains all data accessible anywhere from the application.
 */
public class Session {

    //Singleton
    private static Session ourInstance = new Session();
    //Connected employee
    private Employee employee;

    /**
     * Private default constructor.
     */
    private Session() { }

    /**
     * Get the connected employee.
     * @return connected employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Set the connected employee.
     * @param employee connected Employee
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Get instance of the session.
     * @return session
     */
    public static Session getInstance() {
        return ourInstance;
    }

}
