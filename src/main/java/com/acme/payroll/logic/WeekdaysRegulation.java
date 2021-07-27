package com.acme.payroll.logic;

import com.acme.payroll.model.Roster;
import com.acme.payroll.model.WorkUnit;
import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Implements particular rules for Weekdays rate option
 * @author xavier
 */
public interface WeekdaysRegulation extends Regulation {

    final BigDecimal EARLY_MORNING_WAGE = new BigDecimal(config.getString("WeekdaysRegulation.EARLY_MORNING_WAGE"));
    final BigDecimal DAY_WAGE = new BigDecimal(config.getString("WeekdaysRegulation.DAY_WAGE"));
    final BigDecimal NIGHT_WAGE = new BigDecimal(config.getString("WeekdaysRegulation.NIGHT_WAGE"));
    
    Function<WorkUnit, BigDecimal> calculateEarlyMorning = wu -> new BigDecimal(wu.getDuration().toHours()).multiply(EARLY_MORNING_WAGE);
    Function<WorkUnit, BigDecimal> calculateDay = wu -> new BigDecimal(wu.getDuration().toHours()).multiply(DAY_WAGE);
    Function<WorkUnit, BigDecimal> calculateNight = wu -> new BigDecimal(wu.getDuration().toHours()).multiply(NIGHT_WAGE);

    public Function<Roster, Roster> calculateWeekDaysTotal = roster -> {
        BigDecimal ammountEarlyMorning = roster.getWorkedTimes().stream().filter(isWeekDay).filter(isOnEarlyMorning).map(calculateEarlyMorning).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal ammountDay = roster.getWorkedTimes().stream().filter(isWeekDay).filter(isOnDay).map(calculateDay).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal ammountNight = roster.getWorkedTimes().stream().filter(isWeekDay).filter(isOnNight).map(calculateNight).reduce(BigDecimal.ZERO, BigDecimal::add);
        roster.setTotalAmmount(roster.getTotalAmmount().add(ammountEarlyMorning.add(ammountDay).add(ammountNight)));
        return roster;
    };

}
