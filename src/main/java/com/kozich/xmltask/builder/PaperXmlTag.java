package com.kozich.xmltask.builder;

public enum PaperXmlTag {
    PAPERS("papers"),
    MONTHLY_PERIODICAL("monthly-periodical"),
    NOT_MONTHLY_PERIODICAL("not-monthly-periodical"),
    ID("id"),
    SOURCE("source"),
    CAPACITY("capacity"),
    CHARS("chars"),
    COLOR("color"),
    DATE("date"),
    GLOSSY("glossy"),
    INDEX("index"),
    MONTHLY("monthly"),
    NUMBER("number"),
    TITLE("title"),
    TYPE("type");

    private String value;

    PaperXmlTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
