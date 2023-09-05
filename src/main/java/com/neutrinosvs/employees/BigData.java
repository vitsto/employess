package com.neutrinosvs.employees;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class BigData {

    record Person(String firstName, String lastName, long salary, String state, char gender) {
    }

    public static void main(String[] args) {
        try {

//            Big data summing and domain models with Streams API
            /*
            long startTime = System.currentTimeMillis();
            Long result =
                    Files.lines(Path.of("src/Hr5m/Hr5m.csv")).parallel()
                            .skip(1)
//                            .limit(10)
                            .map(s -> s.split(","))
//                            .map(arr -> arr[25])
//                            .mapToLong(Long::parseLong)
//                            .sum();
                            .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32]))
                            .collect(Collectors.summingLong(Person::salary));
//                            .collect(Collectors.summingLong(sal -> Long.parseLong(sal)));
            long endTime = System.currentTimeMillis();
//                    .collect(Collectors.counting());
            System.out.printf("$%,d.00%n", result);
            System.out.println(endTime - startTime);
*/

            // ----------------------------------------------------------
            //grouping records
            Map<String, List<Person>> result = Files.lines(Path.of("src/Hr5m/Hr5m.csv"))
                    .skip(1)
                    .limit(100)
                    .map(s -> s.split(","))
                    .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32], a[5].charAt(0)))
                    .collect(groupingBy(Person::state, TreeMap::new, Collectors.toList()));


            TreeMap<String, List<String>> collect = Files.lines(Path.of("src/Hr5m/Hr5m.csv"))
                    .skip(1)
                    .limit(100)
                    .map(s -> s.split(","))
                    .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32], a[5].charAt(0)))
//                    .collect(Collectors.groupingBy(Person::state, TreeMap::new, Collectors.toList()));
                    .collect(groupingBy(Person::state, TreeMap::new, Collectors.mapping(Person::firstName, Collectors.toList())));
            System.out.println(result);
            System.out.println(collect);

            // ----------------------------------------------------------
            //summing by groups
            /*
//            Map<String, String> result1 =
            Files.lines(Path.of("src/Hr5m/Hr5m.csv"))
                    .skip(1)
//                    .limit(10)
                    .map(s -> s.split(","))
                    .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32]))
                    .collect(groupingBy(Person::state, TreeMap::new,
//                            collectingAndThen(summingLong(Person::salary), s -> String.format("$%,d.00%n", s))));
//                            collectingAndThen(summingLong(Person::salary), s -> NumberFormat.getCurrencyInstance(Locale.US).format(s))));
                            collectingAndThen(summingLong(Person::salary), NumberFormat.getCurrencyInstance(Locale.US)::format)))
                    .forEach((state, salary) -> System.out.printf("%s -> %s%n", state, salary));
//            System.out.println(result1);

*/
            // ----------------------------------------------------------
            //nested groupings
            Map<String, Map<Character, String>> result1 = Files.lines(Path.of("src/Hr5m/Hr5m.csv"))
                    .skip(1)
//                    .limit(10)
                    .map(s -> s.split(","))
                    .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32], a[5].charAt(0)))
                    .collect(
                            groupingBy(Person::state, TreeMap::new,
                                    groupingBy(Person::gender,
                                            collectingAndThen(
                                                    summingLong(Person::salary),
                                                    NumberFormat.getCurrencyInstance(Locale.US)::format))
                                    //Map<String(state), Map<Character(gender), String(formatted-salary)>>
                            ));
//                    .forEach((state, salary) -> System.out.printf("%s -> %s%n", state, salary));
            System.out.println(result1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
