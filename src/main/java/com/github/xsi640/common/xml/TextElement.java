package com.github.xsi640.common.xml;

/**
 * TextElement，内容为字符串
 */
public class TextElement extends Element {
    private String content;

    public TextElement(String content) {
        super();
        this.content = content;
    }

    @Override
    public String getFormattedContent(int indentLevel) {
        StringBuilder sb = new StringBuilder();
        OutputUtilities.xmlIndent(sb, indentLevel);
        sb.append(content);
        return sb.toString();
    }

    public String getContent() {
        return content;
    }
}
