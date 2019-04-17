package com.rbkmoney.logback.mask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class MaskingMessageWithPattern {

    public static String maskMessage(String message, Pattern multilinePattern) {
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
