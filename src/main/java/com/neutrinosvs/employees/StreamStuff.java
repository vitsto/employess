package com.neutrinosvs.employees;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.function.Predicate.not;


public class StreamStuff {
    public static void main(String[] args) {
        String peopleText = """
                Flinstone, Fred, 1/1/1900, Programmer, {locpd=2500,yoe=10,iq=140}
                Flinstone, Fred, 1/1/1900, Programmer, {locpd=4000,yoe=10,iq=140}
                Flinstone, Fred, 1/1/1900, Programmer, {locpd=5000,yoe=10,iq=140}
                Flinstone, Fred, 1/1/1900, Programmer, {locpd=6000,yoe=10,iq=140}
                Flinstone, Fred, 1/1/1900, Programmer, {locpd=7000,yoe=10,iq=140}
                Flinstone, Fred, 1/1/1900, Programmer, {locpd=9000,yoe=10,iq=140}
                Flinstone, Fred, 1/1/1900, Programmerzzzz, {locpd=10000,yoe=10,iq=140}
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

        int totalSalary = peopleText.lines()
                .map(Employee::createEmployee)
                .map(e -> (Employee) e)
                .filter(e -> e.getSalary() > 5000 && e.getSalary() < 10000)
//                .filter(not(e -> e instanceof Programmer || e.getLastName().equals("N/A")))
//                .filter(not(e -> e.getFirstName().equals("N/A")))
                .collect(Collectors.toSet())
                .stream()
                .sorted(comparing(Employee::getLastName)
                        .thenComparing(Employee::getFirstName)
                        .thenComparing(Employee::getSalary))
                .mapToInt(StreamStuff::showEmpAndGetSalary)
                .sum();
        System.out.println(totalSalary);

        System.out.println("-------------- additional techniques");

        //additional techniques
        Predicate<String> dummyEmployeeSelector = s -> s.contains("Programmerzzzz");
//        Predicate<Employee> dummySelector = employee -> "N/A".equals(employee.getLastName()); // "N/A" is first to defend from NPE
        Predicate<Employee> dummySelector = employee -> employee.getLastName().equals("N/A"); // "N/A" is first to defend from NPE
        Predicate<Employee> overFiveKSelector = e -> e.getSalary() > 5000;
        Predicate<Employee> noDummiesAndOverFiveK = dummySelector.negate().and(overFiveKSelector);
        int sum = peopleText.lines()
//                .filter(dummyEmployeeSelector.negate())
                .map(Employee::createEmployee)
                .map(e -> (Employee) e)
                .filter(noDummiesAndOverFiveK)
                .collect(Collectors.toSet())
                .stream()
                .sorted(comparing(Employee::getLastName)
                        .thenComparing(Employee::getFirstName)
                        .thenComparing(Employee::getSalary))
                .mapToInt(StreamStuff::showEmpAndGetSalary)
                .sum();
        System.out.println(totalSalary);

        System.out.println("\n-------------- Flattening  streams of streams");
        peopleText.lines()
                .map(Employee::createEmployee)
                .map(e -> (Employee) e)
                .map(Employee::getFirstName)
                .map(firstName -> firstName.split(""))
                .flatMap(Arrays::stream)
                .map(String::toLowerCase)
                .distinct()
                .forEach(System.out::print);

        //alternatives to filter
        System.out.println("-------------- Alternatives to filter");
        boolean allOver3K = peopleText.lines()
                .map(Employee::createEmployee)
                .map(e -> (Employee) e)
                .filter(dummySelector.negate())
                .noneMatch(e -> e.getSalary() < 0); //anyMatch, allMatch
        System.out.println(allOver3K);

        Optional<Employee> optionalEmployee = peopleText.lines()
                .map(Employee::createEmployee)
                .map(e -> (Employee) e)
                .filter(dummySelector.negate())
                .findFirst(); //findAny

        System.out.println(optionalEmployee.map(Employee::getFirstName).orElse("not found"));

        //the map reduce pattern
        System.out.println("--------------------The map reduce pattern");

        OptionalDouble resultAverage = peopleText.lines()
                .map(Employee::createEmployee)
                .map(e -> (Employee) e)
                .filter(noDummiesAndOverFiveK)
                .collect(Collectors.toSet())
                .stream()
                .sorted(comparing(Employee::getLastName)
                        .thenComparing(Employee::getFirstName)
                        .thenComparing(Employee::getSalary))
                .skip(5)
                .mapToInt(StreamStuff::showEmpAndGetSalary)
                .average();
        System.out.println(resultAverage.orElse(0));

        OptionalInt resultMax = peopleText.lines()
                .map(Employee::createEmployee)
                .map(e -> (Employee) e)
                .filter(noDummiesAndOverFiveK)
                .collect(Collectors.toSet())
                .stream()
                .sorted(comparing(Employee::getLastName)
                        .thenComparing(Employee::getFirstName)
                        .thenComparing(Employee::getSalary))
                .skip(5)
                .mapToInt(StreamStuff::showEmpAndGetSalary)
                .max();
        System.out.println(resultMax.orElse(0));

        long resultSumWithReduce = peopleText.lines()
//                .filter(dummyEmployeeSelector.negate())
                .map(Employee::createEmployee)
                .map(e -> (Employee) e)
                .filter(noDummiesAndOverFiveK)
                .collect(Collectors.toSet())
                .stream()
                .sorted(comparing(Employee::getLastName)
                        .thenComparing(Employee::getFirstName)
                        .thenComparing(Employee::getSalary))
                .skip(5)
                .mapToInt(StreamStuff::showEmpAndGetSalary)
                .reduce(0, (a, b) -> a + b);
        System.out.println(resultSumWithReduce);

        long resultMaxWithReduce = peopleText.lines()
//                .filter(dummyEmployeeSelector.negate())
                .map(Employee::createEmployee)
                .map(e -> (Employee) e)
                .filter(noDummiesAndOverFiveK)
                .collect(Collectors.toSet())
                .stream()
                .sorted(comparing(Employee::getLastName)
                        .thenComparing(Employee::getFirstName)
                        .thenComparing(Employee::getSalary))
                .skip(5)
                .mapToInt(StreamStuff::showEmpAndGetSalary)
                .reduce(0, (a, b) -> a > b ? a : b);
        System.out.println(resultMaxWithReduce);

        long resultMinWithReduce = peopleText.lines()
//                .filter(dummyEmployeeSelector.negate())
                .map(Employee::createEmployee)
                .map(e -> (Employee) e)
                .filter(noDummiesAndOverFiveK)
                .collect(Collectors.toSet())
                .stream()
                .sorted(comparing(Employee::getLastName)
                        .thenComparing(Employee::getFirstName)
                        .thenComparing(Employee::getSalary))
                .skip(5)
                .mapToInt(StreamStuff::showEmpAndGetSalary)
                .reduce((a, b) -> a < b ? a : b).orElse(-1);
        System.out.println(resultMinWithReduce);

        Optional<String> reduceStrings = Stream.of("tom", "jack", "sam")
                .reduce((a, b) -> a.toUpperCase().concat("_").concat(b.toUpperCase()));
        System.out.println(reduceStrings.orElse(""));

        Optional<String> reduceStrings2 = Stream.of("tom")
                .reduce((a, b) -> a.toUpperCase().concat("_").concat(b.toUpperCase()));
        System.out.println(reduceStrings2.orElse(""));

//        Extra materials

//        Collection<String> nums = Set.of("one", "two", "three", "four");
//        nums.stream()
//                .map(String::hashCode)
//                .map(Integer::toHexString)
//                .forEach(System.out::println);


//        record Car(String make, String model){}
//
//        Stream.of(new Car("Ford", "Bronco"), new Car("Tesla", "X"), new Car("Tesla", "3"))
//                .filter(c -> "Tesla".equals(c.make))
//                .forEach(System.out::println);

//        String myVar = null;
//        Stream.ofNullable(myVar)
//                .forEach(System.out::println);

//        IntStream.rangeClosed(1, 5)
//                .mapToObj(String::valueOf)
//                .map(s -> s.concat("-"))
//                .forEach(System.out::println);

//        String[] names = {"terry", "sam", "jack"};
//        Arrays.stream(names)
//                .map(String::toUpperCase)
//                .forEach(System.out::println);

//        try {
//            Files.lines(Path.of("src/main/java/com/neutrinosvs/employees/employees.txt"))
//                    .forEach(System.out::println);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }

    public static int showEmpAndGetSalary(IEmployee e) {
        System.out.println(e);
        return e.getSalary();
    }
}
