package com.kozich.xmltask.builder;

import com.kozich.xmltask.entity.*;
import com.kozich.xmltask.exception.PaperException;
import com.kozich.xmltask.parser.FilePathParser;
import com.kozich.xmltask.validator.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;

public class PaperDomBuilder extends AbstractPaperBuilder {
    private DocumentBuilder documentBuilder;
    private final static Logger logger = LogManager.getLogger();
    private final static String PARSE_EXCEPTION = "Error with xml parsing";
    private final static String WAY_EXCEPTION = "File does not exist or is not valid";
    private final static String MONTHLY_PERIODICAL_INFO = "All Monthly papers were collected from xmlFile";
    private final static String NOT_MONTHLY_PERIODICAL_INFO = "All Not Monthly papers were collected from xmlFile";

    public PaperDomBuilder() throws PaperException {
        super();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new PaperException(e);
        }
    }

    @Override
    public void buildPeriodicals(String filePath) throws PaperException {
        if (!DataValidator.isValid(filePath)) {
            throw new PaperException(WAY_EXCEPTION);
        }
        String path = FilePathParser.filePath(filePath);
        Document document;
        try {
            document = documentBuilder.parse(path);
            Element root = document.getDocumentElement();
            String notMonthlyPeriodicalTag = PaperXmlTag.NOT_MONTHLY_PERIODICAL.getValue();
            String monthlyPeriodicalTag = PaperXmlTag.MONTHLY_PERIODICAL.getValue();
            NodeList notMonthlyPeriodical = root.getElementsByTagName(notMonthlyPeriodicalTag);
            NodeList monthlyPeriodical = root.getElementsByTagName(monthlyPeriodicalTag);
            for (int i = 0; i < notMonthlyPeriodical.getLength(); i++) {
                Element currentElement = (Element) notMonthlyPeriodical.item(i);
                NotMonthlyPeriodical currentPeriodical = new NotMonthlyPeriodical();
                createPeriodical(currentElement, currentPeriodical);
                periodicals.add(currentPeriodical);
            }
            logger.log(Level.INFO, MONTHLY_PERIODICAL_INFO);

            for (int i = 0; i < monthlyPeriodical.getLength(); i++) {
                Element currentElement = (Element) monthlyPeriodical.item(i);
                MonthlyPeriodical currentPeriodical = new MonthlyPeriodical();
                createPeriodical(currentElement, currentPeriodical);
                periodicals.add(currentPeriodical);
            }
            logger.log(Level.INFO, NOT_MONTHLY_PERIODICAL_INFO);

        } catch (SAXException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new PaperException(PARSE_EXCEPTION, e);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new PaperException(e);
        }
    }

    private void createPeriodical(Element currentElement, AbstractPeriodical periodical) {
        String id = currentElement.getAttribute(PaperXmlTag.ID.getValue());
        String source = currentElement.getAttribute(PaperXmlTag.SOURCE.getValue());
        String title = getElementTextContent(currentElement, PaperXmlTag.TITLE.getValue());
        TypePeriodical type = TypePeriodical.valueOf(getElementTextContent(currentElement, PaperXmlTag.TYPE.getValue()).toUpperCase());
        NodeList charsElement = currentElement.getElementsByTagName(PaperXmlTag.CHARS.getValue());
        Element current = (Element) charsElement.item(0);
        int capacity = Integer.parseInt(getElementTextContent(current, PaperXmlTag.CAPACITY.getValue()));
        boolean color = Boolean.parseBoolean(getElementTextContent(current, PaperXmlTag.COLOR.getValue()));
        boolean glossy = Boolean.parseBoolean(getElementTextContent(current, PaperXmlTag.GLOSSY.getValue()));
        boolean index = Boolean.parseBoolean(getElementTextContent(current, PaperXmlTag.INDEX.getValue()));
        CharsPeriodical chars = new CharsPeriodical(color, capacity, glossy, index);

        if (!source.isBlank()) {
            periodical.setSource(source);
        }

        periodical.setId(id);
        periodical.setTitle(title);
        periodical.setType(type);
        periodical.setChars(chars);

        if (periodical.getClass() == MonthlyPeriodical.class) {
            MonthlyPeriodical monthlyPeriodical = (MonthlyPeriodical) periodical;
            boolean monthly = Boolean.parseBoolean(getElementTextContent(currentElement, PaperXmlTag.MONTHLY.getValue()));
            LocalDate date = LocalDate.parse(getElementTextContent(currentElement, PaperXmlTag.DATE.getValue()));
            monthlyPeriodical.setDate(date);
            monthlyPeriodical.setMonthly(monthly);
        } else {
            NotMonthlyPeriodical notMonthlyPeriodical = (NotMonthlyPeriodical) periodical;
            int number = Integer.parseInt(getElementTextContent(currentElement, PaperXmlTag.NUMBER.getValue()));
            notMonthlyPeriodical.setNumber(number);
        }
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }
}
