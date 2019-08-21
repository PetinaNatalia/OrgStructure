import java.io.*;
import java.util.GregorianCalendar;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

       // Для создания файла со структурой создаем Директора и начальников отделов
        Employee chief1 = new Employee("Иванов Иван Иванович",
                new GregorianCalendar(1980,11, 22), Gender.M,
                    "111-12-12", "Директор", Departments.WITHOUTDEPARTMENT, new Employee(),
                        new GregorianCalendar(1996, 1, 12),200_000);

        Employee chief2 = new Employee("Сидоров Сергей Петрович",
                new GregorianCalendar(1976,8, 10), Gender.M,
                   "777-13-12", "Начальник отдела", Departments.DEPARTMENT1, new Employee(),
                        new GregorianCalendar(2016, 5, 19),150_000);

        Employee chief3 = new Employee("Веселкина Ирина Александровна",
                new GregorianCalendar(1985,3, 4), Gender.F,
                    "555-16-10", "Начальник отдела", Departments.DEPARTMENT2, chief1,
                         new GregorianCalendar(2000, 2, 9),50_000);

        // Загружаем их в HashSet
        HashSet<Employee> employees = new HashSet<>();
        employees.add(chief1);
        employees.add(chief2);
        employees.add(chief3);

        // Создаем файл со структорой
        Employee.loadStructureToFile("Structure.txt", employees);

        // Загружаем структуру из файла
        HashSet<Employee> downloadStr = Employee.readStructureFromFile("Structure.txt");

        // Выводим ее на печать
        StructureOperator.printStr(downloadStr);

        // Начинаем работать со структурой
        StructureOperator operator = new StructureOperator(downloadStr);
        operator.operationsMenu();

        // Записываем измененную структуру в файл
        Employee.loadStructureToFile("Structure.txt", operator.structure);

        // Еще раз считываем файл и выводим на печать, чтобы проверить, применились ли изменения
        downloadStr = Employee.readStructureFromFile("Structure.txt");
        StructureOperator.printStr(downloadStr);
    }


}
