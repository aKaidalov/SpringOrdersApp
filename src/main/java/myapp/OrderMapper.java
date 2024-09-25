package myapp;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderMapper {

    public Order parse(String input) {
        Map<String, String> readyAttributes = prepareAttributes(input);
        return createOrderObj(readyAttributes);
    }

    private Map<String, String> prepareAttributes(String input) {
        Map<String, String> attributes = new LinkedHashMap<>();
        String[] readyString = prepareString(input);

        for (String s : readyString) {
            String[] keyAndValue = s.split(":");
            String attributeName = keyAndValue[0].replace("\"", "").trim();
            String attributeValue = keyAndValue[1].replace("\"", "").trim();
            attributes.put(attributeName, attributeValue);
        }

        return attributes;
    }

    private String[] prepareString(String input) {
        input = input.replace("{", "");
        input = input.replace("}", "");
        input = input.trim();

        return input.split(",");
    }

    private Order createOrderObj(Map<String, String> attributes) {
        Order order = new Order(attributes.get("orderNumber"));
        if (attributes.containsKey("id") && !"null".equals(attributes.get("id"))) {
            order.setId(Long.parseLong(attributes.get("id")));
        }
        return order;
    }

    public String stringify(Order order) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("orderNumber", order.getOrderNumber());

        if (order.getId() != null) {
            map.put("id", String.valueOf(order.getId()));
        }

        String pairs = map.entrySet().stream()
                .map(e -> {
                    if ("id".equals(e.getKey())) {
                        return String.format("\"%s\":%s", e.getKey(), e.getValue());
                    } else {
                        return String.format("\"%s\":\"%s\"", e.getKey(), e.getValue());
                    }
                })
                .collect(Collectors.joining(", "));

        return String.format("{%s}", pairs);
    }
}