public class Employee {
    private int id;
    private final String lastName;
    private final String firstName;
    private final String patronymic;
    private final String login;
    private final String password;
    private String position;
    private double salary;

    public Employee(String lastName, String firstName, String patronymic, String login, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
        this.position = position;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

}
