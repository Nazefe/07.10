import java.util.Calendar;
import java.util.Scanner;

public class Main {
    private DbConnectionImpl dbConnection;
    private Scanner scanner;

    public void run() {
        dbConnection = new DbConnectionImpl();
        scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Do you want to login or register?");
            System.out.println("1. Login");
            System.out.println("2. Register");
            int choice = scanner.nextInt();

            if (choice == 1) {
                login();
            } else if (choice == 2) {
                register();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void login() {
        System.out.println("Enter login:");
        String login = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        Employee employee = dbConnection.getEmployeeByLoginAndPassword(login, password);

        if (employee != null) {
            greetEmployee(employee);
            displayEmployeeData(employee);
        } else {
            System.out.println("Invalid login or password. Please try again.");
        }
    }

    private void register() {
        System.out.println("Enter first name:");
        String firstName = scanner.next();
        System.out.println("Enter last name:");
        String lastName = scanner.next();
        System.out.println("Enter patronymic:");
        String patronymic = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        Employee employee = new Employee(lastName, firstName, patronymic, generateLogin(lastName, firstName, patronymic), password);
        dbConnection.addEmployee(employee);

        System.out.println("Registration successful!");
    }

    private void greetEmployee(Employee employee) {
        String greeting;
        int hour = getHour();

        if (hour >= 4 && hour < 12) {
            greeting = "Good morning, " + employee.getFirstName() + "!";
        } else if (hour >= 12 && hour < 17) {
            greeting = "Good day, " + employee.getFirstName() + "!";
        } else if (hour >= 17 && hour < 22) {
            greeting = "Good evening, " + employee.getFirstName() + "!";
        } else {
            greeting = "Good night, " + employee.getFirstName() + "!";
        }

        System.out.println(greeting);
    }

    private void displayEmployeeData(Employee employee) {
        System.out.println("Employee data:");
        System.out.println("ID: " + employee.getId());
        System.out.println("Name: " + employee.getFirstName() + " " + employee.getLastName() + " " + employee.getPatronymic());
        System.out.println("Position: " + employee.getPosition());
        System.out.println("Salary: " + employee.getSalary() + " rubles");

        if (employee.getPosition().equals("Manager")) {
            displayAllSalaries();
        } else if (employee.getPosition().equals("Administrator")) {
            displayAllLoginsAndPasswords();
        }
    }

    private void displayAllSalaries() {
        System.out.println("All salaries:");
        for (Employee employee : dbConnection.getAllEmployees()) {
            System.out.println(employee.getFirstName() + " " + employee.getLastName() + ": " + employee.getSalary() + " rubles");
        }
    }

    private void displayAllLoginsAndPasswords() {
        System.out.println("All logins and passwords:");
        for (Employee employee : dbConnection.getAllEmployees()) {
            System.out.println(employee.getLogin() + ": " + employee.getPassword());
        }
    }

    private int getHour() {
        var calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    private String generateLogin(String lastName, String firstName, String patronymic) {
        String login = lastName.toLowerCase() + "." + firstName.substring(0, 1).toLowerCase() + "." + patronymic.substring(0, 1).toLowerCase();
        return login;
    }
}
