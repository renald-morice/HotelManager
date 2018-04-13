package Controller;

import Model.Employee;

public class Session {
    private static Session ourInstance = new Session();

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    private Employee employee;

    public static Session getInstance() {
        return ourInstance;
    }

    private Session() {
    }
}
