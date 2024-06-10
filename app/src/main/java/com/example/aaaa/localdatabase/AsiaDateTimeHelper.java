package com.example.aaaa.localdatabase;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AsiaDateTimeHelper {
    private ZoneId asiaZone;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public AsiaDateTimeHelper(String asiaZoneId) {
        this.asiaZone = ZoneId.of(asiaZoneId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getYear() {
        ZonedDateTime now = ZonedDateTime.now(asiaZone);
        return now.getYear();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getMonth() {
        ZonedDateTime now = ZonedDateTime.now(asiaZone);
        return now.getMonthValue();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getDay() {
        ZonedDateTime now = ZonedDateTime.now(asiaZone);
        return now.getDayOfMonth();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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