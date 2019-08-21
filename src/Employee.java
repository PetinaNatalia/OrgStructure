import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.function.Predicate;

class Employee implements Serializable {
    String FIO;
    Calendar dateOfBirth;
    Gender gender;
    String tel;
    String position;
    Departments  dep;
    Employee chief;
    Calendar dateOfEmploy;

    public void setSalary(double salary) {
        this.salary = salary;
    }

    double salary;
    int days;


    Employee(){
        this.FIO = "нет";
    }

    Employee(String FIO, Calendar dateOfBirth, Gender gender,
                String tel, String position, Departments  dep, Employee chief,
                     Calendar dateOfEmploy, double salary){
        this.FIO = FIO;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.tel = tel;
        this.position = position;
        this.dep = dep;
        this.chief = chief;
        this.dateOfEmploy = dateOfEmploy;
        this.salary = salary;
        this.days = (int)(new GregorianCalendar().getTimeInMillis() - this.dateOfEmploy.getTimeInMillis())/1000/3600/24;
    }

    @Override
    public String toString(){
        return this.FIO + " " + this.position + " " + this.dep + " " + this.tel + " " + this.salary;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Employee)){
            return false;
        }

        Employee tmp = (Employee) o;
        return tmp.FIO.equals(this.FIO) && tmp.dateOfBirth.equals(this.dateOfBirth) && tmp.gender.equals(this.gender) && tmp.tel.equals(this.tel) &&
                     tmp.position.equals(this.position) && tmp.dep.equals(this.dep) && tmp.dateOfEmploy.equals(this.dateOfEmploy) &&
                            tmp.salary == this.salary;
    }

    @Override
    public int hashCode(){
        int code = 17;
        code = 31 * code + this.FIO.hashCode();
        code = 31 * code + this.dateOfBirth.hashCode();
        code = 31 * code + this.gender.hashCode();
        code = 31 * code + this.tel.hashCode();
        code = 31 * code + this.position.hashCode();
        code = 31 * code + this.dep.hashCode();
        code = 31 * code + this.dateOfEmploy.hashCode();
        code = 31 * code + (int) this.salary;
        return code;
    }


    public static void getByPredicate(HashSet<Employee> hashSet, Predicate<Employee> predicate){
        for (Employee e : hashSet){
            if (predicate.test(e)){
                System.out.println(e.toString());
            }
        }
    }

    public static void loadStructureToFile(String filename, HashSet <Employee> emp) throws IOException
    {
        File file = new File(filename);
        FileOutputStream fos;
        ObjectOutputStream oos;
        if(file.exists()){
            file.delete();
        }

        fos = new FileOutputStream(file);
        oos = new ObjectOutputStream(fos);

        for(Object obj : emp) {
            oos.writeObject(obj);
        }
        oos.close();
        fos.close();
    }

    public static HashSet<Employee> readStructureFromFile(String filename) throws IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashSet<Employee> list = new HashSet<>();
        Object obj;
        try{
            while ((obj = ois.readObject()) != null ) {
                list.add((Employee) obj);
            }
        } catch (EOFException e) {
            System.out.println("Список сотрудников загружен");
        }
        ois.close();
        fis.close();
        return list;
    }
}
