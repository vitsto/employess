package com.neutrinosvs.employees;

import java.util.LinkedList;
import java.util.List;

public class Experiments {
    public static void main(String[] args) {
        String peopleText = """
                Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
                Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
                Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
                Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
                Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
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

//        peopleText.lines()
//            .map(s -> Employee.createEmployee(s))
//            .forEach(System.out::println);

//        stringList.stream().sorted(Comparator.naturalOrder()).forEach(System.out::print);

        List<String> stringList = new LinkedList<>(List.of("cc", "ddd", "bb", "cc", "aaaa"));
        //stateful
//        stringList.stream().sorted((o1, o2) -> {
//            System.out.println("1 stage: " + o1 + " " + o2 + ";");
//            return o1.compareTo(o2);
//        }).forEach(s -> {
//            System.out.println("2 stage:" + s);
//        });

        //stateless
//        stringList.stream().map(s -> {
//            System.out.println("1 stage:" + s);
//            return s.length();
//        }).forEach(s -> {
//            System.out.println("2 stage:" + s);
//        });

        stringList
                .stream()
                .map(e -> {
                    System.out.println("Map");
                    return e.toUpperCase();
                })
                .filter(e -> {
                    System.out.println("Filter");
                    return e.length() > 2;
                })
                .sorted()
                .forEach(s -> {
                    System.out.println("forEach");
                });


    }
}