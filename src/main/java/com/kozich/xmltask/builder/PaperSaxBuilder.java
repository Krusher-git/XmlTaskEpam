package com.kozich.xmltask.builder;

import com.kozich.xmltask.exception.PaperException;
import com.kozich.xmltask.parser.FilePathParser;
import com.kozich.xmltask.validator.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class PaperSaxBuilder extends AbstractPaperBuilder {
    private final static Logger logger = LogManager.getLogger();
    private XMLReader reader;
    private PaperSaxHandler handler;
    private final static String WAY_EXCEPTION = "File does not exist or is not valid";
    private PapersErrorHandler errorHandler;
    private final static String ERROR_MESSAGE = "Error with parsing";

    public PaperSaxBuilder() {
        super();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        handler = new PaperSaxHandler();
        errorHandler = new PapersErrorHandler();
        try {
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
        } catch (ParserConfigurationException | SAXException e) {
            logger.error("Error in PlantSaxBuilder: " + e.getMessage());
        }
        reader.setContentHandler(handler);
        reader.setErrorHandler(errorHandler);
    }

    @Override
    public void buildPeriodicals(String filePath) throws PaperException {
        if (!DataValidator.isValid(filePath)) {
            throw new PaperException(WAY_EXCEPTION);
        }
        String path = FilePathParser.filePath(filePath);
        try {
            reader.parse(path);
        } catch (IOException | SAXException e) {
            logger.log(Level.ERROR, ERROR_MESSAGE, e);
        }
        periodicals = handler.getPapers();
    }
}
