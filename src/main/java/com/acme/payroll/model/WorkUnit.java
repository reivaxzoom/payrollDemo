package com.acme.payroll.model;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Group working time periods and days.
 * @author xavier
 */
public class WorkUnit {

    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
    private Duration duration;

    public WorkUnit(String day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = Duration.between(startTime, endTime);
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "WorkUnit{" + "day=" + day + ", startTime=" + startTime + ", endTime=" + endTime + ", duration=" + duration.toHours() + '}';
    }
}
