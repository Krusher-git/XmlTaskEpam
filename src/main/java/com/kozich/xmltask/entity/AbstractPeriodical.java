package com.kozich.xmltask.entity;

public class AbstractPeriodical {
    private String title;
    private TypePeriodical type;
    private CharsPeriodical chars;
    private String id;
    private String source;
    private final static String DEFAULT_WEBSITE = "www.google.com";

    public AbstractPeriodical() {
        source = DEFAULT_WEBSITE;
    }

    public AbstractPeriodical(String title, TypePeriodical type, CharsPeriodical chars, String id, String source) {
        this.title = title;
        this.type = type;
        this.chars = chars;
        this.id = id;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TypePeriodical getType() {
        return type;
    }

    public void setType(TypePeriodical type) {
        this.type = type;
    }

    public CharsPeriodical getChars() {
        return chars;
    }

    public void setChars(CharsPeriodical chars) {
        this.chars = chars;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "AbstractPeriodical{" +
                "title='" + title + '\'' +
                ", type=" + type +
                ", chars=" + chars +
                ", id='" + id + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
