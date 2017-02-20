/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.service.webcontent.util;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.w3c.dom.Node.*;

@Slf4j
public class ParserUtil {

    private short type;

    public static Set parseAttributes(String content) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(content)));
        Element rootElement = document.getDocumentElement();
        Set attributeSet = new HashSet();
        getAttributeValues(document.getDocumentElement(), "", attributeSet);
        return attributeSet;
    }

    static void getAttributeValues(Node node, String indent, Set attributeSet) {
        String nodeName = node.getNodeName();
        String parentNodeName = node.getParentNode().getNodeName();
        log.debug(indent + " Node: " + nodeName);
        log.debug(indent + " parent Node: " + parentNodeName);
        short type = node.getNodeType();

        System.out.println(indent + " Node Type: " + nodeType(type));
        if (type == ELEMENT_NODE) {
            NamedNodeMap nodeMap = node.getAttributes();
            for (int j = 0; j < nodeMap.getLength(); j++) {
                final Attr attribute = (Attr) nodeMap.item(j);
                final String name = attribute.getName();
                final String value = attribute.getValue();
                log.debug(indent + " Attribute Name is: " + name);
                log.debug(indent + " Attribute Content is: " + value);
                attributeSet.add(value);
            }

        }
        NodeList list = node.getChildNodes();
        if (list.getLength() > 0) {
            log.debug(indent + " Child Nodes of " + nodeName + " are:");
            for (int i = 0; i < list.getLength(); i++) {
                getAttributeValues(list.item(i), indent + "  ", attributeSet);
            }
        }
    }

    public static Set<String> evaluateEL(String expression) {
        String regex = "\\$\\{(\\w+)\\}";
        Set<String> matches = new HashSet<String>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            String key = matcher.group(1);
            log.debug(key);
            matches.add(key);
        }
        return matches;
    }

    static String nodeType(short type) {
        switch (type) {
            case ELEMENT_NODE:
                return "Element";
            case DOCUMENT_TYPE_NODE:
                return "Document type";
            case ENTITY_NODE:
                return "Entity";
            case ENTITY_REFERENCE_NODE:
                return "Entity reference";
            case NOTATION_NODE:
                return "Notation";
            case TEXT_NODE:
                return "Text";
            case COMMENT_NODE:
                return "Comment";
            case CDATA_SECTION_NODE:
                return "CDATA Section";
            case ATTRIBUTE_NODE:
                return "Attribute";
            case PROCESSING_INSTRUCTION_NODE:
                return "Attribute";
            default:
                return "Unidentified";

        }
    }
}
