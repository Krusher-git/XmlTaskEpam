package com.kozich.xmltask.entity;

public class NotMonthlyPeriodical extends AbstractPeriodical {
    private int number;

    public NotMonthlyPeriodical() {
    }

    public NotMonthlyPeriodical(String title, TypePeriodical type, CharsPeriodical chars, String id, String source, int number) {
        super(title, type, chars, id, source);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
