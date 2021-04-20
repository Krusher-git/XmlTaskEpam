package com.kozich.xmltask.builder;

import com.kozich.xmltask.entity.AbstractPeriodical;
import com.kozich.xmltask.exception.PaperException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPaperBuilder {
    protected Set<AbstractPeriodical> periodicals;

    public AbstractPaperBuilder() {
        periodicals = new HashSet<>();
    }

    public Set<AbstractPeriodical> getPeriodicals() {
        return periodicals;
    }

    public abstract void buildPeriodicals(String path) throws PaperException;
}
