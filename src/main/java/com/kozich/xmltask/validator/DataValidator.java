package com.kozich.xmltask.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class DataValidator {
    private final static String SCHEMA_LOCATION = "schema/schema.xsd";
    private final static Logger logger = LogManager.getLogger();

    public static boolean isValid(String path) {
        ClassLoader loader = DataValidator.class.getClassLoader();
        URL location = loader.getResource(path);
        String filePath = new File(location.getFile()).getAbsolutePath();
        location = loader.getResource(SCHEMA_LOCATION);
        File schemaFile = new File(location.getFile());
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        try {
            Schema schema = factory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(filePath);
            validator.validate(source);
        } catch (SAXException e) {
            logger.log(Level.ERROR, "Exception: " + e.getMessage());
            return false;
        } catch (IOException e) {
            logger.log(Level.ERROR, "Exception: " + e.getMessage());
        }
        return true;
    }
}
