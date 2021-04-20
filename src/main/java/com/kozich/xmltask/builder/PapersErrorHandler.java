package com.kozich.xmltask.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class PapersErrorHandler implements ErrorHandler {
    private final static Logger logger = LogManager.getLogger();
    @Override
    public void warning(SAXParseException exception) throws SAXException {
        logger.warn(exception.getMessage());
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        logger.error(exception.getMessage());
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        logger.fatal(exception.getMessage());
    }
}
