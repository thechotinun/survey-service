package com.survey.v1.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtils {
    private final Logger logger;

    public LoggerUtils(Class<?> clazz) {
        this.logger = LogManager.getLogger(clazz);
    }

    public void info(String message) {
        logger.info("💾 => " + message);
    }

    public void error(String message) {
        logger.error("❌ => " + message);
    }

    public void debug(String message) {
        logger.debug("🐞 => " + message);
    }

    public void warn(String message) {
        logger.warn("⚠️ => " + message);
    }

    public void error(String message, Throwable throwable) {
        logger.error("❌ => " + message, throwable);
    }
}