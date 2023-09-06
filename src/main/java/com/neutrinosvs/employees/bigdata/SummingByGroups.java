package com.neutrinosvs.employees.bigdata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.TreeMap;

import static java.util.stream.Collectors.*;

public class SummingByGroups {
    public static void main(String[] args) {
        //summing by groups
        try {
            //Map<String, String> result1 =
            Files.lines(Path.of("src/Hr5m/Hr5m.csv"))
                    .skip(1)
                    //.limit(10)
                    .map(s -> s.split(","))
                    .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32], a[5].charAt(0)))
                    .collect(groupingBy(Person::state, TreeMap::new,
                            //collectingAndThen(summingLong(Person::salary), s -> String.format("$%,d.00%n", s))));
                            //collectingAndThen(summingLong(Person::salary), s -> NumberFormat.getCurrencyInstance(Locale.US).format(s))));
                            collectingAndThen(summingLong(Person::salary), NumberFormat.getCurrencyInstance(Locale.US)::format)))
                    .forEach((state, salary) -> System.out.printf("%s -> %s%n", state, salary));
            //System.out.println(result1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
