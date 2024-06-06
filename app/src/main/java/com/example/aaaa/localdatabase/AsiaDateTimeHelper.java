package com.example.aaaa.localdatabase;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AsiaDateTimeHelper {
    private ZoneId asiaZone;

    public AsiaDateTimeHelper(String asiaZoneId) {
        this.asiaZone = ZoneId.of(asiaZoneId);
    }

    public int getYear() {
        ZonedDateTime now = ZonedDateTime.now(asiaZone);
        return now.getYear();
    }

    public int getMonth() {
        ZonedDateTime now = ZonedDateTime.now(asiaZone);
        return now.getMonthValue();
    }

    public int getDay() {
        ZonedDateTime now = ZonedDateTime.now(asiaZone);
        return now.getDayOfMonth();
    }

    public String getFullDate() {
        ZonedDateTime now = ZonedDateTime.now(asiaZone);
        String pattern = "yyyyMMdd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return now.format(formatter);
    }
}
// how to call
/*
AsiaDateTimeHelper helper = new AsiaDateTimeHelper("Asia/Kolkata");

        int year = helper.getYear();
        int month = helper.getMonth();
        int day = helper.getDay();
        String fullDate = helper.getFullDate();

        System.out.println("Year: " + year);
        System.out.println("Month: " + month);
        System.out.println("Day: " + day);
        System.out.println("Full Date: " + fullDate);
 */