package com.acme.payroll.model;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Group Name, worked times and pay amount in a roster structure  for calculation
 * @author xavier
 */
public class Roster {

    private String name;
    private List<WorkUnit> workedTimes;
    private BigDecimal totalAmmount;

    public Roster(String name, List<WorkUnit> workedTimes) {
        this.name = name;
        this.workedTimes = workedTimes;
        this.totalAmmount=BigDecimal.ZERO;
    }

    public static class RosterBuilder {
        private Map<String, Map<String, String>> data;
        public  RosterBuilder setData(Map<String, Map<String, String>> data) {
            this.data = data;
            return this;
        }

        Function<Map.Entry<String, String>, WorkUnit> convertToWorkUnit = x -> {
            String day = x.getKey();
            String start = x.getValue().split("-")[0];
            String end = x.getValue().split("-")[1];
            return new WorkUnit(day, LocalTime.parse(start), LocalTime.parse(end));
        };

        Function<Map.Entry<String, Map<String, String>>, Roster> convertToRoster = (e -> {
            Map<String, String> timeWorked = e.getValue();
            List<WorkUnit> worksUnit = timeWorked.entrySet().stream().map(convertToWorkUnit).collect(Collectors.toList());
            Roster r = new Roster(e.getKey(), worksUnit);
            return r;
        });

        public List<Roster> build() {
            List<Roster> t = data.entrySet().stream().map(convertToRoster).collect(Collectors.toList());
            return t;
        }
    }
    
    
    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public List<WorkUnit> getWorkedTimes() {return workedTimes;}

    public void setWorkedTimes(List<WorkUnit> workedTimes) {this.workedTimes = workedTimes;}

    public BigDecimal getTotalAmmount() {return totalAmmount;}

    public void setTotalAmmount(BigDecimal totalAmmount) {this.totalAmmount = totalAmmount;}


    @Override
    public String toString() {
        return "Roster{" + "name=" + name + ", totalAmmount=" + totalAmmount + '}';
    }
}
