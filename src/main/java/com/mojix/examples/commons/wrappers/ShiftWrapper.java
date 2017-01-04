package com.mojix.examples.commons.wrappers;

import java.io.Serializable;

/**
 * Created by achambi on 11/6/16.
 * Shift wrapper class.
 */
public class ShiftWrapper extends ThingPropertyValueWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    public boolean active;
    public Long startTimeOfDay;
    public Long endTimeOfDay;
    public String daysOfWeek;

    public ShiftWrapper(boolean active, Long startTimeOfDay, Long endTimeOfDay, String daysOfWeek, Long id, String name, String code) {
        super(id, name, code);
        this.active = active;
        this.startTimeOfDay = startTimeOfDay;
        this.endTimeOfDay = endTimeOfDay;
        this.daysOfWeek = daysOfWeek;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getStartTimeOfDay() {
        return startTimeOfDay;
    }

    public void setStartTimeOfDay(Long startTimeOfDay) {
        this.startTimeOfDay = startTimeOfDay;
    }

    public Long getEndTimeOfDay() {
        return endTimeOfDay;
    }

    public void setEndTimeOfDay(Long endTimeOfDay) {
        this.endTimeOfDay = endTimeOfDay;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
}
