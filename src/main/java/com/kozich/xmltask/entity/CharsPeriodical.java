package com.kozich.xmltask.entity;

public class CharsPeriodical {
    private boolean color;
    private int capacity;
    private boolean glossy;
    private boolean index;

    public CharsPeriodical() {
    }

    public CharsPeriodical(boolean color, int capacity, boolean glossy, boolean index) {
        this.color = color;
        this.capacity = capacity;
        this.glossy = glossy;
        this.index = index;
    }

    public boolean getColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean getGlossy() {
        return glossy;
    }

    public void setGlossy(boolean glossy) {
        this.glossy = glossy;
    }

    public boolean getIndex() {
        return index;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

}
