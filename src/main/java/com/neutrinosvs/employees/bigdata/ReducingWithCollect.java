package com.neutrinosvs.employees.bigdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.*;

public class ReducingWithCollect {
    public static void main(String[] args) {
        try {
            //nested groupings
            Map<String, Map<Character, String>> result1 = Files.lines(Path.of("src/Hr5m/Hr5m.csv"))
                    .skip(1)
                    //.limit(10)
                    .map(s -> s.split(","))
                    .map(a -> new PersonBD(a[2], a[4], new BigDecimal(a[25]), a[32], a[5].charAt(0)))
                    .collect(
                            groupingBy(PersonBD::state, TreeMap::new,
                                    groupingBy(PersonBD::gender,
                                            collectingAndThen(
                                                    reducing(BigDecimal.ZERO, PersonBD::salary, BigDecimal::add),
                                                    NumberFormat.getCurrencyInstance(Locale.US)::format))
                                    //Map<String(state), Map<Character(gender), String(formatted-salary)>>
                            ));
            //.forEach((state, salary) -> System.out.printf("%s -> %s%n", state, salary));
            System.out.println(result1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
