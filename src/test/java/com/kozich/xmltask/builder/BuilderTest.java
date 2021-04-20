package com.kozich.xmltask.builder;

import com.kozich.xmltask.exception.PaperException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BuilderTest {
    @Test
    public void PaperDomBuilderTest() throws PaperException {
        int expected = 16;
        AbstractPaperBuilder builder = PaperBuilderFactory.createPaperBuilder("DOM");
        builder.buildPeriodicals("data/periodicalstest.xml");
        int actual = builder.getPeriodicals().size();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void PaperSaxBuilderTest() throws PaperException {
        int expected = 16;
        AbstractPaperBuilder builder = PaperBuilderFactory.createPaperBuilder("SAX");
        builder.buildPeriodicals("data/periodicalstest.xml");
        int actual = builder.getPeriodicals().size();
        Assert.assertEquals(actual, expected);
    }
}
