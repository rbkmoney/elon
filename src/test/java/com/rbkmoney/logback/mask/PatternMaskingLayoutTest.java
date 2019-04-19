package com.rbkmoney.logback.mask;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PatternMaskingLayoutTest {
    private PatternMaskingLayout patternMaskingLayout = new PatternMaskingLayout();

    @Before
    public void init() {

        patternMaskingLayout.setPattern("%-5p [%d{ISO8601}] %m%n");

        String[] maskPatterns = {"\\b\\d{6}([\\d\\s]{2,9})\\d{4}\\b", "(\\b\\d{3}\\b)"};
        for (String maskPattern : maskPatterns) {
            patternMaskingLayout.addMaskPattern(maskPattern);
        }

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset();
        patternMaskingLayout.setContext(loggerContext);
        patternMaskingLayout.start();
    }

    @Test
    public void testPatternMaskingLayout() {
        Calendar calendar = new GregorianCalendar(2019, Calendar.APRIL, 15, 14, 15, 16);

        String msg = "http://localhost/?COMMAND=PREAUTH&VERSION=1.0&PAN=3412345678909876&PAN2=9876543219987654&EXPDATE=2012&TERMID=1235111&AMOUNT=1000&CURRENCY=643&INVOICE=invoice_id.1&CVV2=123&RRN=904792140574&CONDITION=3&TDSDATA=";

        ILoggingEvent loggingEvent = createLoggingEvent(Level.DEBUG, calendar.getTime(), msg);

        assertEquals("DEBUG [2019-04-15 14:15:16,000] http://localhost/?COMMAND=PREAUTH&VERSION=1.0&PAN=341234******9876&PAN2=987654******7654&EXPDATE=2012&TERMID=1235111&AMOUNT=1000&CURRENCY=***&INVOICE=invoice_id.1&CVV2=***&RRN=904792**0574&CONDITION=3&TDSDATA=" + System.lineSeparator(),
                patternMaskingLayout.doLayout(loggingEvent));
    }

    ILoggingEvent createLoggingEvent(Level logLevel, Date date, String msg) {

        return new ILoggingEvent() {

            @Override
            public String getThreadName() {
                return null;
            }

            @Override
            public Level getLevel() {
                return logLevel;
            }

            @Override
            public String getMessage() {
                return null;
            }

            @Override
            public Object[] getArgumentArray() {
                return new Object[0];
            }

            @Override
            public String getFormattedMessage() {
                return msg;
            }

            @Override
            public String getLoggerName() {
                return "loggerName";
            }

            @Override
            public LoggerContextVO getLoggerContextVO() {
                return null;
            }

            @Override
            public IThrowableProxy getThrowableProxy() {
                return null;
            }

            @Override
            public StackTraceElement[] getCallerData() {
                return null;
            }

            @Override
            public boolean hasCallerData() {
                return false;
            }

            @Override
            public Marker getMarker() {
                return null;
            }

            @Override
            public Map<String, String> getMDCPropertyMap() {
                return null;
            }

            @Override
            public Map<String, String> getMdc() {
                return null;
            }

            @Override
            public long getTimeStamp() {
                return date.getTime();
            }

            @Override
            public void prepareForDeferredProcessing() {

            }
        };
    }

}
