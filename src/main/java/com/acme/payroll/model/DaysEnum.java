package com.acme.payroll.model;

import java.time.DayOfWeek;

/**
 * Days Enum with abbreviations.
 * @author xavier
 */
public enum DaysEnum {

    MONDAY("MO", DayOfWeek.MONDAY),
    TUESDAY("TU", DayOfWeek.TUESDAY),
    WEDNESDAY("WE", DayOfWeek.WEDNESDAY),
    THURSDAY("TH", DayOfWeek.THURSDAY),
    FRIDAY("FR", DayOfWeek.FRIDAY),
    SATURDAY("SA", DayOfWeek.SATURDAY),
    SUNDAY("SU", DayOfWeek.SUNDAY);

    private final String abbreviation;
    private final DayOfWeek day;

    /**
     *
     * @param abbreviation String
     * @param numero Integer
     */
    private DaysEnum(final String abbreviation, final DayOfWeek day) {
        this.abbreviation = abbreviation;
        this.day = day;
    }

    
    /**
     * return day of week from on abbreviation
     * @param abbreviation Integer
     * @return DayOfWeek
     */
    public static DayOfWeek getDayOfWeek(final String abbreviation) {
        for (DaysEnum d : values()) {
            if (d.getAbbreviation().equals(abbreviation)) {
                return d.getDay();
            }
        }
        return null;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public DayOfWeek getDay() {
        return day;
    }
}
