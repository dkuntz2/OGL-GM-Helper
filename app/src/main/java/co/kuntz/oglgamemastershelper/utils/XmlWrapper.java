package co.kuntz.oglgamemastershelper.utils;


import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlWrapper {

    private static SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

    private String name;
    private Map<String, String> attributes = new HashMap<>();
    private List<Object> content = new LinkedList<>();

    public XmlWrapper(String name) {
        this.name = name;
    }

    public static XmlWrapper parse(String xml) {
        try {
            InputSource source = new InputSource(new StringReader(xml));
            SAXParser parser = saxParserFactory.newSAXParser();
            MapNodeHandler handler = new MapNodeHandler();
            parser.parse(source, handler);

            return handler.root;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public String getName() { return name; }
    public List<Object> getContent() { return content; }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String findString(String expression) {
        XmlWrapper node = find(expression);
        return node == null ? null : node.stringValue();
    }

    private String stringValue() {
        if (content.size() == 1 && content.get(0) == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (Object o : content) {
            builder.append(o.toString());
        }

        return builder.toString().trim();
    }

    public XmlWrapper find(String expression) {
        String[] paths = expression.split("/");
        LinkedList<String> tokens = new LinkedList<>(Arrays.asList(paths));

        return find(tokens);
    }

    private XmlWrapper find(LinkedList<String> tokens) {
        if (tokens.isEmpty()) {
            return this;
        }

        String first = tokens.getFirst();
        if (".".equals(restOf(tokens))) {
            return find(restOf(tokens));
        }

        for (XmlWrapper node : childNodes()) {
            if ("*".equals(first) || first.equals(node.name)) {
                return node.find(restOf(tokens));
            }
        }

        return null;
    }

    private List<XmlWrapper> childNodes() {
        List<XmlWrapper> nodes = new LinkedList<>();

        for (Object o : content) {
            if (o instanceof XmlWrapper) {
                nodes.add((XmlWrapper) o);
            }
        }

        return nodes;
    }

    private LinkedList<String> restOf(LinkedList<String> tokens) {
        LinkedList<String> newTokens = new LinkedList<>(tokens);
        newTokens.removeFirst();
        return newTokens;
    }

    public List<XmlWrapper> findAll(String expression) {
        String[] paths = expression.split("/");
        LinkedList<String> tokens = new LinkedList<>(Arrays.asList(paths));
        List<XmlWrapper> nodes = new LinkedList<>();
        findAll(tokens, nodes);
        return nodes;
    }

    private void findAll(LinkedList<String> tokens, List<XmlWrapper> nodes) {
        if (tokens.isEmpty()) {
            nodes.add(this);
        } else {
            String first = tokens.getFirst();
            if (".".equals(first)) {
                findAll(restOf(tokens), nodes);
            }

            for (XmlWrapper node : childNodes()) {
                if ("*".equals(first) || first.equals(node.name)) {
                    node.findAll(restOf(tokens), nodes);
                }
            }
        }
    }

    private static class MapNodeHandler extends DefaultHandler {
        private static Pattern NON_WHITE_SPACE = Pattern.compile("\\S");

        private Stack<XmlWrapper> stack = new Stack<>();
        public XmlWrapper root;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            XmlWrapper node = new XmlWrapper(qName);

            for (int i = 0; i < attributes.getLength(); i++) {
                node.attributes.put(attributes.getQName(i), attributes.getValue(i));
            }

            if ("true".equals(node.attributes.get("nil"))) {
                node.content.add(null);
            }

            if (!stack.isEmpty()) {
                stack.peek().content.add(node);
            }

            stack.push(node);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            XmlWrapper pop = stack.pop();
            if (stack.isEmpty()) {
                root = pop;
            }
        }

        @Override
        public void characters(char[] chars, int start, int length) throws SAXException {
            String value = new String(chars, start, length);

            Matcher matcher = NON_WHITE_SPACE.matcher(value);
            if (value.length() > 0 && matcher.find()) {
                stack.peek().content.add(value);
            }
        }
    }

}
