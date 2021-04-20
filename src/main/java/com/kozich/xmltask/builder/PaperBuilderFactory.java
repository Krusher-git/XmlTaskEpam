package com.kozich.xmltask.builder;

import com.kozich.xmltask.exception.PaperException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PaperBuilderFactory {
    private final static Logger logger = LogManager.getLogger();
    private final static String ERROR_MESSAGE = "Exception with parser type";
    private enum ParserType {
        DOM,
        SAX,
        STAX
    }

    public PaperBuilderFactory() {
    }

    public static AbstractPaperBuilder createPaperBuilder(String parserType) throws PaperException {
        try {
            ParserType parser = ParserType.valueOf(parserType);
            switch (parser) {
                case DOM:
                    return new PaperDomBuilder();
                case SAX:
                    return new PaperSaxBuilder();
                case STAX:
                    return new PaperStaxBuilder();
                default:
                    throw new PaperException(ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException | PaperException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new PaperException(e);
        }
    }
}
