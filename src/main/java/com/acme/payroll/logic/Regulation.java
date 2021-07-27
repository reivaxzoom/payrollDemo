package com.acme.payroll.logic;

import com.acme.payroll.model.DaysEnum;
import com.acme.payroll.model.Roster;
import java.time.LocalTime;
import java.util.function.Predicate;
import com.acme.payroll.model.WorkUnit;
import java.util.ResourceBundle;

/**
 * Collect all business rules abstractly encoded for concrete implementations
 * @author xavier
 */
@FunctionalInterface
public interface Regulation {

    Roster calculateSalary(Roster roster);
    final ResourceBundle config = ResourceBundle.getBundle("config");
    
    final LocalTime startEarlyMorning = LocalTime.parse("00:01");
    final LocalTime endEarlyMorning = LocalTime.parse("09:00");
    final LocalTime startDay = LocalTime.parse("09:01");
    final LocalTime endDay = LocalTime.parse("18:00");
    final LocalTime startNight = LocalTime.parse("18:01");
    final LocalTime endNight = LocalTime.parse("00:00").minusNanos(1l);
    

    final Predicate<WorkUnit> isWeekDay = inputDay -> (
            DaysEnum.MONDAY.getAbbreviation().equals(inputDay.getDay())
            || DaysEnum.TUESDAY.getAbbreviation().equals(inputDay.getDay())
            || DaysEnum.WEDNESDAY.getAbbreviation().equals(inputDay.getDay())
            || DaysEnum.THURSDAY.getAbbreviation().equals(inputDay.getDay())
            || DaysEnum.FRIDAY.getAbbreviation().equals(inputDay.getDay()));
    final Predicate<WorkUnit> isWeekEnd = inputDay -> (DaysEnum.SATURDAY.getAbbreviation().equals(inputDay.getDay())
            || DaysEnum.SUNDAY.getAbbreviation().equals(inputDay.getDay()));

   

    final Predicate<WorkUnit> isOnEarlyMorning = wu -> (wu.getStartTime().isAfter(startEarlyMorning) && wu.getStartTime().isBefore(endEarlyMorning))
            && (wu.getEndTime().isAfter(startEarlyMorning) && wu.getEndTime().isBefore(endEarlyMorning));

    final Predicate<WorkUnit> isOnDay = wu -> (
               (wu.getStartTime().isAfter(startDay) || wu.getStartTime().compareTo(startDay) == 0)
            && (wu.getStartTime().isBefore(endDay) || wu.getStartTime().compareTo(endDay) == 0)
            && (wu.getEndTime().isAfter(startDay) || wu.getEndTime().compareTo(startDay) == 0)
            && (wu.getEndTime().isBefore(endDay) || wu.getEndTime().compareTo(endDay)==0));

    final Predicate<WorkUnit> isOnNight = wu -> (
            (wu.getStartTime().isAfter(startNight) || wu.getStartTime().compareTo(startNight)==0 )
            &&( wu.getStartTime().isBefore(endNight)|| wu.getStartTime().compareTo(endNight)==0 )
            && (wu.getEndTime().isAfter(startNight) || wu.getEndTime().compareTo(startNight)==0  )
            && (wu.getEndTime().isBefore(endNight) ||  wu.getEndTime().compareTo(endNight)==0 ));

}
