package com.kozich.xmltask.builder;

import com.kozich.xmltask.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class PaperSaxHandler extends DefaultHandler {
    private final static Logger logger = LogManager.getLogger();
    private final static String WARN_QNAME = "Current qName does not fit the condition";
    private final static String DEFAULT_WEBSITE = "www.google.com";

    private Set<AbstractPeriodical> papers;
    private EnumSet<PaperXmlTag> tagsWithText;
    private PaperXmlTag currentTag;
    private AbstractPeriodical currentPeriodical;
    private CharsPeriodical charsPeriodical;

    public PaperSaxHandler() {
        papers = new HashSet<>();
        tagsWithText = EnumSet.range(PaperXmlTag.CAPACITY, PaperXmlTag.TYPE);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        String monthlyPeriodicalTag = PaperXmlTag.MONTHLY_PERIODICAL.getValue();
        String notMonthlyPeriodicalTag = PaperXmlTag.NOT_MONTHLY_PERIODICAL.getValue();
        String id = PaperXmlTag.ID.getValue();
        String source = PaperXmlTag.SOURCE.getValue();
        String chars = PaperXmlTag.CHARS.getValue();

        if (monthlyPeriodicalTag.equals(qName)) {
            currentPeriodical = new MonthlyPeriodical();
        } else if (notMonthlyPeriodicalTag.equals(qName)) {
            currentPeriodical = new NotMonthlyPeriodical();
        }

        if (attributes.getLength() == 1) {
            currentPeriodical.setId(attributes.getValue(0));
            currentPeriodical.setSource(DEFAULT_WEBSITE);
        } else if (attributes.getLength() == 2) {
            currentPeriodical.setId(attributes.getValue(id.toLowerCase()));
            currentPeriodical.setSource(attributes.getValue(source.toLowerCase()));
        }
        String name = qName.replace('-', '_');
        PaperXmlTag tag = PaperXmlTag.valueOf(name.toUpperCase());
        if (tagsWithText.contains(tag)) {
            currentTag = tag;
        }
        if (chars.equals(qName)) {
            charsPeriodical = new CharsPeriodical();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String monthlyPeriodicalTag = PaperXmlTag.MONTHLY_PERIODICAL.getValue();
        String notMonthlyPeriodicalTag = PaperXmlTag.NOT_MONTHLY_PERIODICAL.getValue();
        if (notMonthlyPeriodicalTag.equals(qName) || monthlyPeriodicalTag.equals(qName)) {
            currentPeriodical.setChars(charsPeriodical);
            papers.add(currentPeriodical);
            currentPeriodical = null;
            charsPeriodical = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length);
        if (currentTag == null) {
            return;
        }
        switch (currentTag) {
            case TYPE:
                currentPeriodical.setType(TypePeriodical.valueOf(data.toUpperCase()));
                break;
            case TITLE:
                currentPeriodical.setTitle(data);
                break;
            case MONTHLY:
                ((MonthlyPeriodical) currentPeriodical).setMonthly(Boolean.parseBoolean(data));
                break;
            case DATE:
                ((MonthlyPeriodical) currentPeriodical).setDate(LocalDate.parse(data));
                break;
            case NUMBER:
                ((NotMonthlyPeriodical) currentPeriodical).setNumber(Integer.parseInt(data));
                break;
            case COLOR:
                charsPeriodical.setColor(Boolean.parseBoolean(data));
                break;
            case INDEX:
                charsPeriodical.setIndex(Boolean.parseBoolean(data));
                break;
            case GLOSSY:
                charsPeriodical.setGlossy(Boolean.parseBoolean(data));
                break;
            case CAPACITY:
                charsPeriodical.setCapacity(Integer.parseInt(data));
                break;
            case CHARS:
                break;
            default:
                throw new EnumConstantNotPresentException(
                        currentTag.getDeclaringClass(), currentTag.name());
        }
        currentTag = null;
    }
    public Set<AbstractPeriodical> getPapers(){
        return papers;
    }
}
