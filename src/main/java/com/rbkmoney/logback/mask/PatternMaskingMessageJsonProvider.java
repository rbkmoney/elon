package com.rbkmoney.logback.mask;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import net.logstash.logback.composite.AbstractFieldJsonProvider;
import net.logstash.logback.composite.FieldNamesAware;
import net.logstash.logback.composite.JsonWritingUtils;
import net.logstash.logback.fieldnames.LogstashFieldNames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PatternMaskingMessageJsonProvider extends AbstractFieldJsonProvider<ILoggingEvent> implements FieldNamesAware<LogstashFieldNames> {
    private Pattern multilinePattern;
    private List<String> maskPatterns = new ArrayList<>();
    public static final String FIELD_MESSAGE = "message";

    public void addMaskPattern(String maskPattern) {
        maskPatterns.add(maskPattern);
        multilinePattern = Pattern.compile(String.join("|", maskPatterns), Pattern.MULTILINE);
    }

    public PatternMaskingMessageJsonProvider() {
        setFieldName(FIELD_MESSAGE);
    }


    @Override
    public void writeTo(JsonGenerator generator, ILoggingEvent event) throws IOException {
        JsonWritingUtils.writeStringField(generator, getFieldName(), MaskingMessageWithPattern.maskMessage(event.getFormattedMessage(), multilinePattern));
    }

    @Override
    public void setFieldNames(LogstashFieldNames fieldNames) {
        setFieldName(fieldNames.getMessage());
    }

}
