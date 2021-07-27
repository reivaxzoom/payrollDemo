/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.payroll.logic;

import static com.acme.payroll.logic.Regulation.isOnDay;
import static com.acme.payroll.logic.Regulation.isOnEarlyMorning;
import static com.acme.payroll.logic.Regulation.isOnNight;
import com.acme.payroll.model.Roster;
import com.acme.payroll.model.WorkUnit;
import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Implements particular rules for Weekends rate option
 * @author xavier
 */
public interface WeekendsRegulation extends Regulation {

    BigDecimal EARLY_MORNING_WAGE = new BigDecimal(config.getString("WeekendsRegulation.EARLY_MORNING_WAGE"));
    BigDecimal DAY_WAGE = new BigDecimal(config.getString("WeekendsRegulation.DAY_WAGE"));
    BigDecimal NIGHT_WAGE = new BigDecimal(config.getString("WeekendsRegulation.NIGHT_WAGE"));

    Function<WorkUnit, BigDecimal> calculateEarlyMorning = wu -> new BigDecimal(wu.getDuration().toHours()).multiply(EARLY_MORNING_WAGE);
    Function<WorkUnit, BigDecimal> calculateDay = wu -> new BigDecimal(wu.getDuration().toHours()).multiply(DAY_WAGE);
    Function<WorkUnit, BigDecimal> calculateNight = wu -> new BigDecimal(wu.getDuration().toHours()).multiply(NIGHT_WAGE);

    public Function<Roster, Roster> calculateWeekendsTotal = roster -> {
        BigDecimal ammountEarlyMorning = roster.getWorkedTimes().stream().filter(isWeekEnd).filter(isOnEarlyMorning).map(calculateEarlyMorning).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal ammountDay = roster.getWorkedTimes().stream().filter(isWeekEnd).filter(isOnDay).map(calculateDay).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal ammountNight = roster.getWorkedTimes().stream().filter(isWeekEnd).filter(isOnNight).map(calculateNight).reduce(BigDecimal.ZERO, BigDecimal::add);
        roster.setTotalAmmount(roster.getTotalAmmount().add(ammountEarlyMorning.add(ammountDay).add(ammountNight)));
        return roster;
    };
}
