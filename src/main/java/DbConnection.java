import java.sql.Connection;
import java.util.ArrayList;

public interface DbConnection {
    Employee getEmployeeByLoginAndPassword(String login, String password);
    void addEmployee(Employee employee);
    ArrayList<Employee> getAllEmployees();

    void showData(Connection connection);
}
