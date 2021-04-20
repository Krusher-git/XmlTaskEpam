package com.kozich.xmltask.builder;

import com.kozich.xmltask.entity.*;
import com.kozich.xmltask.exception.PaperException;
import com.kozich.xmltask.parser.FilePathParser;
import com.kozich.xmltask.validator.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

public class PaperStaxBuilder extends AbstractPaperBuilder {
    private final static Logger logger = LogManager.getLogger();
    private final static String WAY_EXCEPTION = "File does not exist or is not valid";
    private XMLInputFactory inputFactory;
    private final static String ERROR = "Error due to:";
    private CharsPeriodical chars;

    public PaperStaxBuilder() {
        super();
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public Set<AbstractPeriodical> getPeriodicals() {
        return super.getPeriodicals();
    }

    @Override
    public void buildPeriodicals(String path) throws PaperException {
        if (!DataValidator.isValid(path)) {
            throw new PaperException(WAY_EXCEPTION);
        }
        String filePath = FilePathParser.filePath(path);
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream = new FileInputStream(new File(filePath))) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(PaperXmlTag.MONTHLY_PERIODICAL.getValue())) {
                        AbstractPeriodical periodical = new MonthlyPeriodical();
                        createPeriodical(periodical, reader);
                        periodicals.add(periodical);
                    }
                    if (name.equals(PaperXmlTag.NOT_MONTHLY_PERIODICAL.getValue())) {
                        AbstractPeriodical periodical = new NotMonthlyPeriodical();
                        createPeriodical(periodical, reader);
                        periodicals.add(periodical);
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            logger.log(Level.ERROR, ERROR, e);
            throw new PaperException(e);
        }
    }

    private void createPeriodical(AbstractPeriodical currentPeriodical, XMLStreamReader reader) throws XMLStreamException {
        String id = reader.getAttributeValue(null, PaperXmlTag.ID.getValue());
        String source = reader.getAttributeValue(null, PaperXmlTag.SOURCE.getValue());

        currentPeriodical.setId(id);
        if (source != null) {
            currentPeriodical.setSource(source);
        }
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    if (name.equals(PaperXmlTag.CHARS.getValue())) {
                        chars = new CharsPeriodical();
                    }
                    PaperXmlTag currentTag = PaperXmlTag.valueOf(name.toUpperCase());
                    insertData(reader, currentTag, currentPeriodical);
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (name.equals(PaperXmlTag.CHARS.getValue())) {
                        chars = null;
                    }
                    if (name.equals(PaperXmlTag.MONTHLY_PERIODICAL.getValue()) || name.equals(PaperXmlTag.NOT_MONTHLY_PERIODICAL.getValue())) {
                        return;
                    }
                    break;
            }
        }
    }

    private void insertData(XMLStreamReader reader, PaperXmlTag currentTag, AbstractPeriodical periodical) throws XMLStreamException {
        String data = getTextContent(reader);
        switch (currentTag) {
            case TYPE:
                periodical.setType(TypePeriodical.valueOf(data.toUpperCase()));
                break;
            case TITLE:
                periodical.setTitle(data);
                break;
            case MONTHLY:
                MonthlyPeriodical monthlyPeriodical = (MonthlyPeriodical) periodical;
                monthlyPeriodical.setMonthly(Boolean.parseBoolean(data));
                break;
            case NUMBER:
                NotMonthlyPeriodical notMonthlyPeriodical = (NotMonthlyPeriodical) periodical;
                notMonthlyPeriodical.setNumber(Integer.parseInt(data));
                break;
            case CAPACITY:
                chars.setCapacity(Integer.parseInt(data));
                break;
            case GLOSSY:
                chars.setGlossy(Boolean.parseBoolean(data));
                break;
            case INDEX:
                chars.setIndex(Boolean.parseBoolean(data));
                break;
            case COLOR:
                chars.setColor(Boolean.parseBoolean(data));
                break;
            default:
                break;

        }
    }

    private static String getTextContent(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}




