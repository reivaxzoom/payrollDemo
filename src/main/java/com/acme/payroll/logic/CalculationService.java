package com.acme.payroll.logic;

import com.acme.payroll.model.Roster;
import java.util.function.Function;

/**
 * Implements Regulation 
 * @author xavier
 */

public class CalculationService implements Regulation{

    /**
     * Calculate Weekdays plus weekends salary composing independent functions
     * @param roster Input worktime
     * @return Output worktime
     */
    @Override
    public Roster calculateSalary(Roster roster) {
        Function<Roster,Roster> calculateSalary=WeekdaysRegulation.calculateWeekDaysTotal.andThen(WeekendsRegulation.calculateWeekendsTotal);
        return calculateSalary.apply(roster);
    }
}
