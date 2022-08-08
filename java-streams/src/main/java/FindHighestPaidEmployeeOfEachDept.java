import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class FindHighestPaidEmployeeOfEachDept {

    /**
     * Assume you have list of employees of various departments
     * write a program to find highest paid employee of each department
     * @param args
     */

    public static void main(String[] args) {
        List<Employee> employees = List.of(
                new Employee("Ross" ,"Geller",Instant.now(),Department.IT,new BigDecimal(10000.00)),
                new Employee("Monica" ,"Geller",Instant.now(),Department.MARKETING,new BigDecimal(10001.00)),
                new Employee("Rachel" ,"Green",Instant.now(),Department.SALES,new BigDecimal(10002.00)),
                new Employee("Chandler" ,"Bing",Instant.now(),Department.MARKETING,new BigDecimal(10003.00)),
                new Employee("Joye" ,"Tribyoni",Instant.now(),Department.SALES,new BigDecimal(10004.00)),
                new Employee("Phebe" ,"Buffey",Instant.now(),Department.IT,new BigDecimal(10005.00)),
                new Employee("Ursela" ,"Buffey",Instant.now(),Department.SALES,new BigDecimal(10006.00))
        );

        // Approach 1
        employees.stream().collect(
                Collectors.groupingBy(Employee::getDepartment, Collectors.maxBy(Comparator.comparing(Employee::getSalary)))
        ).forEach((key,value) ->{
            System.out.println(key+ ":"+ value.orElse(null));
        });
        System.out.println("===================");
        // Approach 2
        employees.stream().collect(
                Collectors.groupingBy(Employee::getDepartment,
                        Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Employee::getSalary)))
                )).forEach((key, value) -> {
            System.out.println(key + ":" + value.orElse(null));
        });
        System.out.println("===================");
        // Approach 3
        employees.stream().collect(
                Collectors.groupingBy(Employee::getDepartment,
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Employee::getSalary)), Optional::get)
                )).forEach((key, value) -> {
            System.out.println(key + ":" + value);
        });
    }
}

class Employee {
    String firstName;
    String lastName;
    Instant dob;
    Department department;
    BigDecimal salary;

    public Employee(String firstName, String lastName, Instant dob, Department department, BigDecimal salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.department = department;
        this.salary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Instant getDob() {
        return dob;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", department=" + department +
                ", salary=" + salary +
                '}';
    }
}

enum Department{
    SALES,MARKETING,IT
}


