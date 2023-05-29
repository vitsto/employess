package com.neutrinosvs.employees;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CEO extends Employee implements IEmployee, Apple, Flyer {
    private int avgStockPrice = 0;
    private Flyer flyer = new Pilot(1000, true);
    private final String ceoRegex = "\\w+=(?<avgStockPrice>\\w+)";
    private final Pattern ceoPat = Pattern.compile(ceoRegex);

    public CEO(String personText) {
        super(personText);
        Matcher ceoMat = ceoPat.matcher(peopleMat.group("details"));
        if (ceoMat.find()) {
            this.avgStockPrice = Integer.parseInt(ceoMat.group("avgStockPrice"));
        }
    }

    public int getSalary() {
        return 5000 * avgStockPrice;
    }

    public void fly() {
        flyer.fly();
    }

    public int getHoursFlown() {
        return flyer.getHoursFlown();
    }

    public void setHoursFlown(int hoursFlown) {
        flyer.setHoursFlown(hoursFlown);
    }

    public boolean isIfr() {
        return flyer.isIfr();
    }

    public void setIfr(boolean ifr) {
        flyer.setIfr(ifr);
    }
}
