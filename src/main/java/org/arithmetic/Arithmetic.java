package org.arithmetic;

import java.util.Arrays;
import java.util.List;

public class Arithmetic {

    public String calculate(String expression) {
        if (!hasBrackets(expression) || !hasEvenNumberOfBrackets(expression)) return "Invalid record error";
        if (thereIsNotOperations(expression)) return "0";

        String expressionCopy = expression;
        String operation = pickUpAnOperationWithBracketsFrom(expressionCopy);
        String resultOfOperation = calculateOperationInBrackets(operation);
        expressionCopy = replaceTheSimpleOperationWithResult(expressionCopy, operation, resultOfOperation);

        if (hasBrackets(expressionCopy)){
            return calculate(expressionCopy);
        }

        if (isInteger(expressionCopy)) return removeCommasIn(expressionCopy);
        return expressionCopy;
    }

    private String calculateOperationInBrackets(String operation){
        String operationInBrackets = operation;
        String[] operationDivided = divideExpressionBySpaces(operationInBrackets);
        String simpleOperation = formatSimpleOperationFrom(operationDivided);
        String result = calculateResult(operationDivided);
        operationInBrackets = replaceTheSimpleOperationWithResult(operationInBrackets, simpleOperation, result);
        operationDivided = divideExpressionBySpaces(operationInBrackets);

        if (thereIsAnOperation(operationDivided)){
            return calculateOperationInBrackets(operationInBrackets);
        }
        return result;
    }
    private String pickUpAnOperationWithBracketsFrom(String expression){
        String allSignals = "+-*/";
        String[] expressionDivided = divideExpressionBySpaces(expression);
        StringBuilder operation = new StringBuilder();

        for ( String sign : expressionDivided ){
            if ( sign.equals(")") ) {
                operation.append(")");
                break;
            }
            if ( sign.equals("(") ){
                if ( operation.toString().contains("(") ) operation = new StringBuilder();
                operation.append(sign).append(" ");
            } else if ( isNumeric(sign) || allSignals.contains(sign) ) operation.append(sign).append(" ");
        }

        return operation.toString();
    }

    private String calculateResult(String[] operationDivided){
        double firstOperator = obtainFirstOperatorIn(operationDivided);
        double secondOperator = obtainSecondOperatorIn(operationDivided);
        String operator = obtainSignalIn(operationDivided);

        return switch (operator) {
            case "+" -> String.valueOf(firstOperator + secondOperator);
            case "-" -> String.valueOf(firstOperator - secondOperator);
            case "*" -> String.valueOf(firstOperator * secondOperator);
            case "/" -> String.valueOf(firstOperator / secondOperator);
            default -> "Error";
        };
    }
    private String removeCommasIn(String expression){
        int number = (int)Double.parseDouble(expression);
        return String.valueOf(number);
    }
    private boolean thereIsNotOperations(String expression) {
        return expression
                .replace('(', ' ')
                .replace(')', ' ')
                .trim().isEmpty();
    }
    private boolean hasBrackets(String expression) {
        return expression.startsWith("(") && expression.endsWith(")");
    }
    private boolean hasEvenNumberOfBrackets(String expression) {
        String[] expressionDivided = divideExpressionBySpaces(expression);
        int numberBracketsObtained = Arrays.stream(expressionDivided)
                .filter(it -> it.equals("(") || it.equals(")")).toList().size();
        return numberBracketsObtained % 2 == 0;

    }
    private boolean isInteger(String expression){
        return expression.contains(".0");
    }
    private String[] divideExpressionBySpaces(String expression){
        return expression.split(" ");
    }
    private String replaceTheSimpleOperationWithResult(String expression, String simpleOperation, String result){
        return expression.replace(simpleOperation, result);
    }
    private String formatSimpleOperationFrom(String[] expression){
        return expression[1] + " " + expression[2] + " " + expression[3];
    }
    private boolean thereIsAnOperation(String[] operationDivided){
        return operationDivided.length > 3;
    }
    private double obtainFirstOperatorIn(String[] expression){
        return Double.parseDouble(String.valueOf(expression[1]));
    }
    private double obtainSecondOperatorIn(String[] expression){
        return Double.parseDouble(String.valueOf(expression[3]));
    }
    private String obtainSignalIn(String[] expression){
        return expression[2];
    }
    private boolean isNumeric(String letter){
        try{
            Double.parseDouble(letter);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
