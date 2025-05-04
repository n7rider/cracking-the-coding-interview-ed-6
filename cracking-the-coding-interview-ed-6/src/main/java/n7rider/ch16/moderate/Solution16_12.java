package n7rider.ch16.moderate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * XML Encoding: Since XML is very verbose, you are given a way of encoding it where each tag gets
 * mapped to a pre-defined integer value. The language/grammar is as follows:
 * Element --> Tag Attributes END Children END
 * Attribute --> Tag Value
 * END --> 0
 * Tag --> some predefined mapping to int
 * Value --> string value
 * For example, the following XML might be converted into the compressed string below (assuming a
 * mapping of family -> 1, person ->2, firstName -> 3, lastName -> 4, state
 * -> 5).
 * <family lastName="McDowell" state="CA">
 * <person firstName="Gayle">Some Message</person>
 * </family>
 * Becomes:
 * 1 4 McDowell SCA 0 2 3 Gayle 0 Some Message 0 0
 * Write code to print the encoded version of an XML element (passed in Element and Attribute
 * objects).
 *
 * After finishing solution:
 * The validation makes it look bigger than usual
 *
 * After comparing:
 * Same as mine, but the book doesn't explain how the tags are generated.
 * Passing around StringBuilder as an arg, rather than using a centralized List helps keep the operation thread-safe.
 * Even though the global LL doesn't take contiguous space, being thread-safe is a much bigger advantage.
 */
public class Solution16_12 {
    public static void main(String[] args) {
        Element root1 = null;
        System.out.println(toEncodedVersion(root1));

        Attribute attr1 = new Attribute("lastName", "McDowell");
        Attribute attr2 = new Attribute("state", "CA");
        Attribute attr3 = new Attribute("firstName", "Gayle");
        Element person = new Element("person", "Some Message", List.of(attr3), null);
        Element root2 = new Element("family", null, List.of(attr1, attr2), List.of(person));
        System.out.println(toEncodedVersion(root2));
    }

    static List<String> out = new LinkedList<>();
    static Map<String, Integer> tagMap = new HashMap<>();
    static int count = 1;
    static String toEncodedVersion(Element root) {
        if(root != null) {
            helper(root);
        }
        return toString(out);
    }

    static class Element {
        String name;
        String value;
        List<Attribute> attributes;
        List<Element> children;

        Element(String name, String value, List<Attribute> attributes, List<Element> children) {
            this.name = name;
            this.value = value;
            this.attributes = attributes;
            this.children = children;
        }
    }

    static class Attribute {
        String name;
        String value;

        Attribute(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    private static String toString(List<String> out) {
        if(out == null || out.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(String s: out) {
            stringBuilder.append(s).append(" ");
        }
        return stringBuilder.toString();
    }

    private static void helper(Element element) {
        validateElement(element);
        getAndSetTag(element.name);

        if(element.attributes != null) {
            for (Attribute attr : element.attributes) {
                validateName(attr.name);
                getAndSetTag(attr.name);
                out.add(attr.value);
            }
        }
        out.add(String.valueOf(0));

        if(element.children != null) {
            for (Element child : element.children) {
                helper(child);
            }
        }

        if(element.value != null) {
            out.add(element.value);
        }

        out.add(String.valueOf(0));
    }

    private static void validateName(String name) {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Null name for element");
        }
    }

    private static void validateElement(Element element) {
        if(element == null) {
            throw new IllegalArgumentException("Null element");
        }
        validateName(element.name);
    }

    private static void getAndSetTag(String name) {
        if(name == null || name.trim().isEmpty()) {
            return; // Skip if an element has no string value but just has attributes and children elements
        }

        int tag;
        if(tagMap.containsKey(name)) {
            tag = tagMap.get(name);
        } else {
            tagMap.put(name, count);
            tag = count;
            count++;
        }
        out.add(String.valueOf(tag));
    }

}

/*
Element
    name: string
    value: string
    attributes: Attributes[]
    children: Element[]

Attribute
    name: string
    value: string

a place to store name, tag - Hashmap
then follow the rules

Algorithm:
start at root element
// null check + validations (ele only value or children NOT both)
call rec(root)

a global linked list <string> to store out
a global counter

rec(element):
    tag = getAndSetTag(element.name)

    for each attr in attributes[]
        getAndSetTag(attr.name)
        out.add attr.value
    add 0

    for each ele in element.children
        rec(element)

    add(element.value)

    add 0

getAndSetTag(name):
    if name is null
        skip

    if ele exists in Hashmap
        get tag
    else
        inc counter
        insert tag to hashmap

    out.add tag

 */