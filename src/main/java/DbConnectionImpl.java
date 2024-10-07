import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class DbConnectionImpl implements DbConnection {

    private final Connection connect;

    public DbConnectionImpl() {
        connect = connect();
    }

    @Override
    public Employee getEmployeeByLoginAndPassword(String login, String password) {
        try {
            var request = "SELECT * FROM public.staff WHERE login = ? AND password = ?";
            var preparedStatement = connect.prepareStatement(request);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Employee(
                        resultSet.getString("lastname"),
                        resultSet.getString("firstname"),
                        resultSet.getString("patronymic"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }


    @Override
    public void addEmployee(Employee employee) {
        try {
            var uuid = UUID.randomUUID();
            var request = "INSERT INTO public.staff (id, last_name, first_name, patronymic, login, password, position, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            var preparedStatement = connect.prepareStatement(request);
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getFirstName());
            preparedStatement.setString(4, employee.getPatronymic());
            preparedStatement.setString(5, employee.getLogin());
            preparedStatement.setString(6, employee.getPassword());
            preparedStatement.setString(7, employee.getPosition());
            preparedStatement.setDouble(8, employee.getSalary());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Employee> getAllEmployees() {
        try {
            var request = "SELECT * FROM public.staff";
            var preparedStatement = connect.prepareStatement(request);
            var resultSet = preparedStatement.executeQuery();

            var employees = new ArrayList<Employee>();

            while (resultSet.next()) {
                employees.add(new Employee(
                        resultSet.getString("last_name"),
                        resultSet.getString("first_name"),
                        resultSet.getString("patronymic"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                ));
            }

            return employees;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void showData(Connection connection) {
        try {
            var request = "SELECT * FROM public.staff";
            var preparedStatement = connection.prepareStatement(request);
            var resultSet = preparedStatement.executeQuery();

            System.out.println("Employee data:");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Name: " + resultSet.getString("last_name") + " " + resultSet.getString("first_name") + " " + resultSet.getString("patronymic"));
                System.out.println("Login: " + resultSet.getString("login"));
                System.out.println("Password: " + resultSet.getString("password"));
                System.out.println("Position: " + resultSet.getString("position"));
                System.out.println("Salary: " + resultSet.getDouble("salary") + " rubles");
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/company_db";
            String username = "postgres";
            String password = "admin";
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
