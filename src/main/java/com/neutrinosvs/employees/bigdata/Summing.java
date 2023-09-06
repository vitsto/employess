package com.neutrinosvs.employees.bigdata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class Summing {
    public static void main(String[] args) {
        //Big data summing and domain models with Streams API
        try {
            long startTime = System.currentTimeMillis();
            Long result = Files.lines(Path.of("src/Hr5m/Hr5m.csv")).parallel()
                    .skip(1)
//                            .limit(10)
                    .map(s -> s.split(","))
//                            .map(arr -> arr[25])
//                            .mapToLong(Long::parseLong)
//                            .sum();
                    .map(a -> new Person(a[2], a[4], Long.parseLong(a[25]), a[32], a[5].charAt(0)))
                    .collect(Collectors.summingLong(Person::salary));
//                            .collect(Collectors.summingLong(sal -> Long.parseLong(sal)));
            long endTime = System.currentTimeMillis();
//                    .collect(Collectors.counting());
            System.out.printf("$%,d.00%n", result);
            System.out.println(endTime - startTime);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
