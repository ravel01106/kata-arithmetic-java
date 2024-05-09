package org.arithmetic;

import java.util.Arrays;
import java.util.List;

public class Arithmetic {

    public String calculate(String expression) {
        String result = "";
        String[] expressionDivided = expression.split(" ");
        List<String> bracketsObtained = Arrays.stream(expressionDivided)
                .filter((it) -> it.equals("(") || it.equals(")"))
                .toList();

        boolean thereAreBracketsAtBeginAndAtEnd = expression.startsWith("(") && expression.endsWith(")");
        boolean thereAreEvenNumberOfBrackets = bracketsObtained.size() % 2 == 0;
        if (!thereAreEvenNumberOfBrackets || !thereAreBracketsAtBeginAndAtEnd){
            return "Invalid record error";
        }

        return result;
    }
}
