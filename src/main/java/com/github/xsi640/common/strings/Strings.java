package com.github.xsi640.common.strings;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.quote;

/**
 * String格式化类
 * Usage:
 * <pre>
 *     Strings.format("Hello {firstname} {lastname}!")
 *         .with("firstname", "John")
 *         .with("lastname", "Doe")
 *         .build();
 * </pre>
 */
public final class Strings {

    private Strings() {
    }

    /**
     * Usage:
     * <pre>
     *     Strings.format("Hello {firstname} {lastname}!")
     *         .with("firstname", "John")
     *         .with("lastname", "Doe")
     *         .build();
     * </pre>
     * @param string 要格式化的字符串
     * @return 格式化后的字符串
     */
    public static Builder format(String string) {
        return new Builder(string);
    }

    public static Builder format(String string, boolean strictMode) {
        return new Builder(string, strictMode);
    }

    /**
     * Usage:
     * <pre>
     *     Strings.format("Hello [firstname] [lastname]!", "[", "]")
     *         .with("firstname", "John")
     *         .with("lastname", "Doe")
     *         .build();
     * </pre>
     *
     * @return The formatted string.
     */
    public static Builder format(String string, String prefix, String suffix, boolean strictMode) {
        return new Builder(string, prefix, suffix, strictMode);
    }

    public static class Builder {

        private final Pattern pattern;

        private String baseString;
        private String prefix;
        private String suffix;
        private boolean strictMode = true;

        private Builder(String string) {
            this(string, "{", "}", false);
        }

        public Builder(String string, boolean strictMode) {
            this(string, "{", "}", strictMode);
        }

        private Builder(String string, String prefix, String suffix, boolean strictMode) {
            baseString = string;
            this.prefix = prefix;
            this.suffix = suffix;
            pattern = Pattern.compile(quote(prefix) + ".*?" + quote(suffix));
            this.strictMode = strictMode;
        }

        /**
         * If you set the strict mode to false, the builder won't throw any exception
         * if a key is not found or if a key is still present in the final string.
         *
         * @param active
         * @return
         */
        public Builder strictMode(boolean active) {
            this.strictMode = active;
            return this;
        }

        /**
         * @param key   The key, without the '{}'.
         * @param value The value to put for that key.
         * @return The builder for DSL.
         */
        public Builder with(String key, Object value) {
            if (value == null) value = "";
            if (strictMode && !baseString.contains(prefix + key + suffix))
                throw new KeyNotFoundException(key, baseString);
            baseString = baseString.replace(prefix + key + suffix, value.toString());
            return this;
        }

        /**
         * Create the final string.
         */
        public String build() {
            final Matcher matcher = pattern.matcher(baseString);
            if (strictMode && matcher.find()) {
                throw new MissingKeyException(matcher.group());
            } else {
                return baseString;
            }
        }

    }
}
