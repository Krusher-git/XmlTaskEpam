package com.kozich.xmltask.entity;

import java.time.LocalDate;

public class MonthlyPeriodical extends AbstractPeriodical {
    private boolean monthly;
    private LocalDate date;

    public MonthlyPeriodical() {
    }

    public MonthlyPeriodical(String title, TypePeriodical type, CharsPeriodical chars, String id, String source, boolean monthly, LocalDate date) {
        super(title, type, chars, id, source);
        this.monthly = monthly;
        this.date = date;
    }

    public boolean getMonthly() {
        return monthly;
    }

    public void setMonthly(boolean monthly) {
        this.monthly = monthly;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
