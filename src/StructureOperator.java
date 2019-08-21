import java.util.*;

class StructureOperator {
    HashSet<Employee> structure;

    StructureOperator(HashSet<Employee> structure){
        this.structure = structure;
    }

    void operationsMenu(){
        boolean isCorrect = true;
        do {
            String command = wordRequest("Введите операцию [add/delete/search/report/change/exit]");
            switch (command) {
                case "add":
                    add();
                    break;
                case "delete":
                    delete();
                    break;
                case "search":
                    searchMenu();
                    break;
                case "report":
                    reportMenu();
                    break;
                case "change":
                    change();
                    break;
                case "exit":
                    isCorrect = false;
                    break;
            }
        }while (isCorrect);
    }

    private void add(){
       String FIO = wordRequest("Введите ФИО");
       Calendar dateOfBirth = requestData("рожедия");
       Gender gender = Gender.valueOf(wordRequest("Выбирите пол M/F"));
       String tel = wordRequest("Введите номер телефона");
       String position = wordRequest("Введите должность");
       System.out.println("Выбирите начальника");
       Employee chief = selectEmp();
       Departments  dep = Departments.valueOf(wordRequest("Введите отдел"));
       Calendar dateOfEmploy = requestData("трудоустройства");
       double salary = Double.parseDouble(wordRequest("Введите зарплату"));
       Employee newEmp = new Employee(FIO, dateOfBirth, gender, tel, position, dep, chief, dateOfEmploy, salary);
       this.structure.add(newEmp);
   }

   private void delete(){
       Employee del = selectEmp();
       this.structure.remove(del);
   }

   private Employee selectEmp(){
       String searchWord = wordRequest("Введите фамилию: ");
       Employee e = null;
       for (Employee emp : this.structure){
           if(emp.FIO.contains(searchWord)){
               e = emp;
           }
       }
       return e;
   }

   private void searchMenu(){
        boolean isCorrect = true;
        do {
            String command = wordRequest("Введите параметр поиска [FIO/position/department/chiefFIO/exit]");
            String searchWord;
            switch (command) {
                case "FIO":
                    searchWord = wordRequest("Введите фамилию сотрудника");
                    search((employee) -> employee.FIO.contains(searchWord));
                    break;
                case "position":
                    searchWord = wordRequest("Введите должность сотрудника");
                    search((employee) -> employee.position.contains(searchWord));
                    break;
                case "department":
                    searchWord = wordRequest("Введите название департамента");
                    search((employee) -> employee.dep.toString().contains(searchWord));
                    break;
                case "chiefFIO":
                    searchWord = wordRequest("Введите фамилию начальника");
                    search((employee) -> employee.chief.FIO.contains(searchWord));
                    break;
                case "exit":
                    isCorrect = false;
                    break;
            }
        }while (isCorrect);
    }

   private void search(SearchingParam searchingParam){
       ArrayList<Employee> searched = new ArrayList<>();
       for (Employee e : this.structure){
           if (searchingParam.searching(e)){
               searched.add(e);
           }
       }
       System.out.println("Найдено: " + searched.size());
       printStr(searched);
    }

   private void reportMenu(){
        boolean isCorrect = true;
        do {
            String command = wordRequest("Введите параметр поиска [TopSalary/AverageSalary/TopYears/PredicateBySalary/exit]");
            switch (command) {
                case "TopSalary":
                    sort((o1, o2) -> (int)(o2.salary - o1.salary));
                    break;
                case "AverageSalary":
                    averageSalary ();
                    break;
                case "TopYears":
                    sort((o1, o2) -> (o1.days - o2.days));
                    break;
                case "PredicateBySalary":
                    getByPredicate();
                    break;
                case "exit":
                    isCorrect = false;
                    break;
            }
        }while (isCorrect);
    }

   private void sort(Comparator <Employee> comparator) {
       List<Employee> sortedList = new ArrayList<>(this.structure);
       sortedList.sort(comparator);
       printStr(sortedList);
    }

   private void getByPredicate(){
        Scanner cs = new Scanner(System.in);
        System.out.println("Зарплата больше чем: ");
        int userSal = cs.nextInt();
        Employee.getByPredicate(this.structure,e -> e.salary > userSal);
    }

   private void averageSalary (){
       double avarageSalary = 0;
       for (Employee e : this.structure){
           avarageSalary += e.salary;
       }
       System.out.println(avarageSalary/this.structure.size());
   }

   private void change(){
       Scanner cs = new Scanner(System.in);
       System.out.println("Увеличить зарплату, тем у кого ниже, чем");
       double sal = cs.nextDouble();
       this.structure.stream().filter(e -> e.salary < sal).forEach(e -> e.setSalary(e.salary*2));
   }

    private String wordRequest(String massage){
        Scanner sc = new Scanner(System.in);
        System.out.println(massage);
        return sc.nextLine();
    }

    private GregorianCalendar requestData(String type){
        String dBirth = wordRequest("Введите дату " + type +" (ГГГГ-ММ-ДД)");
        String[] data = dBirth.split("-");
        return new GregorianCalendar(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]));
    }

    static void printStr(Collection<Employee> collection){
        for (Employee e : collection){
            System.out.println(e.toString());
        }
        System.out.println("--------------------------------------------------------------------");
    }
}
