package com.neutrinosvs.employees.bigdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.*;

public class PartitioningVsGrouping {
    public static void main(String[] args) {
        try {
            Map<Boolean, Map<String, Long>> result = Files.lines(Path.of("src/Hr5m/Hr5m.csv"))
                    .skip(1)
//                    .limit(200)
                    .map(s -> s.split(","))
                    .map(a -> new PersonBD(a[2], a[4], new BigDecimal(a[25]), a[32], a[5].charAt(0)))
                    .collect(partitioningBy(p -> p.gender() == 'F',
                            groupingBy(PersonBD::state, counting())));

            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
