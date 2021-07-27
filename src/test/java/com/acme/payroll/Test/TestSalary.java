package com.acme.payroll.Test;

import com.acme.payroll.exception.FileNotFoundException;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import com.acme.payroll.logic.CalculationService;
import com.acme.payroll.logic.Regulation;
import com.acme.payroll.model.Roster;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author xavier
 */
public class TestSalary {

    @Test
    public void morningWeekday() throws FileNotFoundException, IOException, ParseException {
        Map<String, Map<String, String>> data = new HashMap<>();
        Map<String, String> workUnit = new HashMap<>();
        workUnit.put("MO", "10:00-12:00");
        workUnit.put("TU", "10:00-12:00");
        data.put("daffy", workUnit);

        Regulation regulation = new CalculationService();
        List<Roster> rosters = new Roster.RosterBuilder().setData(data).build();
        BigDecimal total= rosters.stream().map(roster -> regulation.calculateSalary(roster)).map(r -> r.getTotalAmmount()).reduce(BigDecimal.ZERO,BigDecimal::add);
        assertThat(total, is(new BigDecimal(60)));
    }
    
    
      @Test
    public void earlyMorningWeekday() throws FileNotFoundException, IOException, ParseException {
        Map<String, Map<String, String>> data = new HashMap<>();
        Map<String, String> workUnit = new HashMap<>();
        workUnit.put("TH", "01:00-03:00");
        data.put("elmer", workUnit);

        Regulation regulation = new CalculationService();
        List<Roster> rosters = new Roster.RosterBuilder().setData(data).build();
        BigDecimal total= rosters.stream().map(roster -> regulation.calculateSalary(roster)).map(r -> r.getTotalAmmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        assertThat(total, is(new BigDecimal(50)));
    }
     
    @Test
    public void dayWeekend() throws FileNotFoundException, IOException, ParseException {
        Map<String, Map<String, String>> data = new HashMap<>();
        Map<String, String> workUnit = new HashMap<>();
        workUnit.put("SA", "14:00-18:00");
        data.put("bugs", workUnit);

        Regulation regulation = new CalculationService();
        List<Roster> rosters = new Roster.RosterBuilder().setData(data).build();
        BigDecimal total= rosters.stream().map(roster -> regulation.calculateSalary(roster)).map(r -> r.getTotalAmmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        assertThat(total, is(new BigDecimal(80)));
    }
    
    @Test
    public void nightWeekend() throws FileNotFoundException, IOException, ParseException {
        Map<String, Map<String, String>> data = new HashMap<>();
        Map<String, String> workUnit = new HashMap<>();
        workUnit.put("SU", "20:00-21:00");
        data.put("Foghorn Leghorn", workUnit);

        Regulation regulation = new CalculationService();
        List<Roster> rosters = new Roster.RosterBuilder().setData(data).build();
        BigDecimal total= rosters.stream().map(roster -> regulation.calculateSalary(roster)).map(r -> r.getTotalAmmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        assertThat(total, is(new BigDecimal(25)));
    }
    
    @Test
    public void periodOverlaped() throws FileNotFoundException, IOException, ParseException {
        Map<String, Map<String, String>> data = new HashMap<>();
        Map<String, String> workUnit = new HashMap<>();
        workUnit.put("SU", "02:00-18:00");
        data.put("Yosemite Sam", workUnit);

        Regulation regulation = new CalculationService();
        List<Roster> rosters = new Roster.RosterBuilder().setData(data).build();
        BigDecimal total= rosters.stream().map(roster -> regulation.calculateSalary(roster)).map(r -> r.getTotalAmmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        assertThat(total, is(BigDecimal.ZERO));
    }
}
