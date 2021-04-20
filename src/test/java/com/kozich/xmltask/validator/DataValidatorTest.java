package com.kozich.xmltask.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DataValidatorTest {
    @Test
    public void isValidTest() {
        String path = "data/periodicalstest.xml";
        boolean actual = DataValidator.isValid(path);
        Assert.assertTrue(actual);
    }

    @Test
    public void isValidWrongTest() {
        String path = "data/periodicalstestWrong.xml";
        boolean actual = DataValidator.isValid(path);
        Assert.assertFalse(actual);
    }
}
