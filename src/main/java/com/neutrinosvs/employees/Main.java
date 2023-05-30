package com.neutrinosvs.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;

public class Main {
    public static void main(String[] args) {
        String peopleText = """
                Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
                Flinstone, Fred, 1/1/1900, Programmerzzzz, {locpd=2000,yoe=10,iq=140}
                Flinstone2, Fred2, 1/1/1900, Programmer, {locpd=1300,yoe=14,iq=100}
                Flinstone3, Fred3, 1/1/1900, Programmer, {locpd=2300,yoe=8,iq=105}
                Flinstone4, Fred4, 1/1/1900, Programmer, {locpd=1630,yoe=3,iq=115}
                Flinstone5, Fred5, 1/1/1900, Programmer, {locpd=5,yoe=10,iq=100}
                Rubble, Barney, 2/2/1905, Manager, {orgSize=300,dr=10}
                Rubble2, Barney2, 2/2/1905, Manager, {orgSize=100,dr=4}
                Rubble3, Barney3, 2/2/1905, Manager, {orgSize=200,dr=2}
                Rubble4, Barney4, 2/2/1905, Manager, {orgSize=500,dr=8}
                Rubble5, Barney5, 2/2/1905, Manager, {orgSize=175,dr=20}
                Flinstone, Wilma, 3/3/1910, Analyst, {projectCount=3}
                Flinstone2, Wilma2, 3/3/1910, Analyst, {projectCount=4}
                Flinstone3, Wilma3, 3/3/1910, Analyst, {projectCount=5}
                Flinstone4, Wilma4, 3/3/1910, Analyst, {projectCount=6}
                Flinstone5, Wilma5, 3/3/1910, Analyst, {projectCount=9}
                Rubble, Betty, 4/4/1915, CEO, {avgStockPrice=300}
                """;

        Matcher peopleMat = Employee.PEOPLE_PAT.matcher(peopleText);

        int totalSalaries = 0;
        IEmployee employee;
        List<IEmployee> employees = new LinkedList<>();
        while (peopleMat.find()) {
            employee = Employee.createEmployee(peopleMat.group());
            employees.add(employee);
        }

        employees.sort((o1, o2) -> {
            if (o1 instanceof Employee emp1 && o2 instanceof Employee emp2) {
                int lnameResult = emp2.lastName.compareTo(emp1.lastName);
                return lnameResult != 0 ? lnameResult : Integer.compare(emp1.getSalary(), (emp2.getSalary()));
            }
            return 0;
        });

//        List<String> undesirables = List.of("Wilma5", "Barney4", "Fred2");
//        removeUndesirables(employees, undesirables);

        for (IEmployee worker : employees) {
           System.out.println(worker);
           totalSalaries += worker.getSalary();
       }

        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        System.out.printf("The total payout should be %s%n", currencyInstance.format(totalSalaries));

//        Weirdo larry = new Weirdo("David", "Larry", LocalDate.of(1950, 1, 1));
//        Weirdo betterWeirdo = new Weirdo(larry.lastName() + "ZZZ", "Larry", LocalDate.of(1950, 1, 1));
//        Apple jake = new Weirdo("Snake", "Jake");
//        System.out.println(larry.firstName());
//        jake.sayHello();
    }

    private static void removeUndesirables(List<IEmployee> employees, List<String> removalNames) {
        for (Iterator<IEmployee> iterator = employees.iterator(); iterator.hasNext(); ) {
            IEmployee worker = iterator.next();
            if (worker instanceof Employee tmpWorker) {
                if (removalNames.contains(tmpWorker.firstName)) {
                    iterator.remove();
                }
            }
        }
    }
}
