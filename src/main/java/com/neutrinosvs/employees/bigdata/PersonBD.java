package com.neutrinosvs.employees.bigdata;

import java.math.BigDecimal;

// The field "salary" is BigDecimal type here
public record PersonBD(String firstName, String lastName, BigDecimal salary, String state, char gender) {
}

