package com.github.xsi640.common.xml;

/**
 * XML转字符串时使用的工具类，提供换行和缩进的辅助操作
 */
public class OutputUtilities {
    private static final String lineSeparator;

    static {
        String ls = System.getProperty("line.separator");
        if (ls == null) {
            ls = "\n";
        }
        lineSeparator = ls;
    }

    private OutputUtilities() {
        super();
    }

    public static void javaIndent(StringBuilder sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append("    ");
        }
    }

    public static void xmlIndent(StringBuilder sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append("  ");
        }
    }

    public static StringBuilder newLine(StringBuilder sb) {
        sb.append(lineSeparator);
        return sb;
    }
}

