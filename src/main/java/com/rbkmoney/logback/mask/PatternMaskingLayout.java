package com.rbkmoney.logback.mask;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;
import org.slf4j.Marker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PatternMaskingLayout extends PatternLayout {

    private Pattern multilinePattern;
    private List<String> maskPatterns = new ArrayList<>();

    public void addMaskPattern(String maskPattern) {
        maskPatterns.add(maskPattern);
        multilinePattern = Pattern.compile(
                maskPatterns.stream()
                        .collect(Collectors.joining("|")),
                Pattern.MULTILINE
        );
    }

    @Override
    public String doLayout(ILoggingEvent event) {

        ILoggingEvent maskedEvent = new ILoggingEvent() {
            @Override
            public String getThreadName() {
                return event.getThreadName();
            }

            @Override
            public Level getLevel() {
                return event.getLevel();
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
                return maskMessage(event.getFormattedMessage());
            }

            @Override
            public String getLoggerName() {
                return event.getLoggerName();
            }

            @Override
            public LoggerContextVO getLoggerContextVO() {
                return event.getLoggerContextVO();
            }

            @Override
            public IThrowableProxy getThrowableProxy() {
                return event.getThrowableProxy();
            }

            @Override
            public StackTraceElement[] getCallerData() {
                return new StackTraceElement[0];
            }

            @Override
            public boolean hasCallerData() {
                return true;
            }

            @Override
            public Marker getMarker() {
                return event.getMarker();
            }

            @Override
            public Map<String, String> getMDCPropertyMap() {
                return event.getMDCPropertyMap();
            }

            @Override
            public Map<String, String> getMdc() {
                return event.getMDCPropertyMap();
            }

            @Override
            public long getTimeStamp() {
                return event.getTimeStamp();
            }

            @Override
            public void prepareForDeferredProcessing() {

            }
        };

        return super.doLayout(maskedEvent);

    }

    private String maskMessage(String message) {
        if (multilinePattern == null) {
            return message;
        }
        StringBuilder sb = new StringBuilder(message);
        Matcher matcher = multilinePattern.matcher(sb);
        while (matcher.find()) {
            IntStream.rangeClosed(1, matcher.groupCount()).forEach(group -> {
                if ((matcher.group(group) != null)) {
                    IntStream.range(matcher.start(group), matcher.end(group))
                            .forEach(i -> sb.setCharAt(i, '*'));
                }
            });
        }
        return sb.toString();
    }

}

