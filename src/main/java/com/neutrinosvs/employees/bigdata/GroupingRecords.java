package com.neutrinosvs.employees.bigdata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class GroupingRecords {
    public static void main(String[] args) {
        try {
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
                    //.collect(Collectors.groupingBy(Person::state, TreeMap::new, Collectors.toList()));
                    .collect(groupingBy(Person::state, TreeMap::new, Collectors.mapping(Person::firstName, Collectors.toList())));
            System.out.println(result);
            System.out.println(collect);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
