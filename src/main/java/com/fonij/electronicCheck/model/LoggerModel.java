package com.fonij.electronicCheck.model;


import org.apache.log4j.*;
import static org.apache.log4j.Level.INFO;

/**
 * complete
 */
public class LoggerModel {

    private Logger logger;


    public LoggerModel(Class clazz) {
        this.logger = Logger.getLogger(clazz);
    }

    public void logInfo(String message) {
        logger.info(message);
    }

    public static void setConsoleAppenderConfiguration() {
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(new PatternLayout("%d [%p|%C|%M|Line:%L] // %m%n"));
        consoleAppender.activateOptions();
        Logger.getRootLogger().addAppender(consoleAppender);
        Logger.getRootLogger().setLevel(INFO);
    }

}

