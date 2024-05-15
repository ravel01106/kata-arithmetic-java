package org.arithmetic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithmeticShould {
    // Rules
    // Devuelve un Error cuando no hay parentesis al principio y al final
    // Devolver un Error cuando no hay un número par de parentesis
    // Devolver 0 si no existe ningún número ni signos dentro de los parentesis
    // Calcular la operación siguiendo las reglas PEMDAS si no hay errores

    // Examples
    // "( )" -> "0"
    // "( ( ) )" -> "0"
    // "( 0 + 1 )" -> "1"
    // "( 0 - 1 )" -> "-1"
    // "( 2 * 1 )" -> "2"
    // "( 6 / 2 )" -> "3"
    // "( 1 + ( 0 + 1 ) )" -> "2"
    // "( ( 2 + 3 ) + ( 3 + 2 ) )" -> "10"
    // "( 1 + ( ( 2 + 3 ) * (4 * 5) ) )" -> "101"
    // "3 + ( 2 * 1 )" -> "Invalid record error"
    // "( 3 + ( 2 * 1 )" -> "Invalid record error"
    private String calculate(String expression) {
        if (!expression.startsWith("(") || !expression.endsWith(")")) return "Invalid record error";
        if (thereIsNotOperations(expression)) return "0";

        String expressionCopy = expression;
        String operation = pickUpAnOperationWithBracketsFrom(expressionCopy);
        String resultOfOperation = calculateOperationInBrackets(operation);
        expressionCopy = replaceTheSimpleOperationWithResult(expressionCopy, operation, resultOfOperation);

        if (isContainBrackets(expressionCopy)){
            return calculate(expressionCopy);
        }

        if (isInteger(expressionCopy)) return removeCommasIn(expressionCopy);
        return expressionCopy;
    }

    private static String calculateOperationInBrackets(String operation){
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
    private static String pickUpAnOperationWithBracketsFrom(String expression){
        String allSignals = "+-*/";
        String[] expressionDivided = expression.split(" ");
        StringBuilder operation = new StringBuilder();
        for (String sign: expressionDivided){
            if (sign.equals(")")){
                operation.append(")");
                break;
            }else if (sign.equals("(")){
                if (operation.toString().contains("(")){
                    operation = new StringBuilder();
                }
                operation.append(sign).append(" ");
            }else if (isNumeric(sign) || allSignals.contains(sign)){
                operation.append(sign).append(" ");
            }
        }
        return operation.toString();
    }
    private static String calculateResult(String[] operationDivided){
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
    private static boolean thereIsNotOperations(String expression) {
        return expression
                .replace('(', ' ')
                .replace(')', ' ')
                .trim().isEmpty();
    }
    private static boolean isContainBrackets(String expression){
        return expression.contains("(") || expression.contains(")");
    }
    private static boolean isInteger(String expression){
        return expression.contains(".0");
    }
    private static String[] divideExpressionBySpaces(String expression){
        return expression.split(" ");
    }
    private static String replaceTheSimpleOperationWithResult(String expression, String simpleOperation, String result){
        return expression.replace(simpleOperation, result);
    }
    private static String formatSimpleOperationFrom(String[] expression){
        return expression[1] + " " + expression[2] + " " + expression[3];
    }
    private static boolean thereIsAnOperation(String[] operationDivided){
        return operationDivided.length > 3;
    }
    private static double obtainFirstOperatorIn(String[] expression){
    return Double.parseDouble(String.valueOf(expression[1]));
    }
    private static double obtainSecondOperatorIn(String[] expression){
        return Double.parseDouble(String.valueOf(expression[3]));
    }
    private static String obtainSignalIn(String[] expression){
        return expression[2];
    }
    private static boolean isNumeric(String letter){
        try{
           Double.parseDouble(letter);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    @Test
    void resolve_not_given_operation_as_zero(){
        assertEquals("0", calculate("( )"));
        assertEquals("0", calculate("( ( ) )"));
    }

    @Test
    void calculate_the_most_simple_sum_operation(){
        assertEquals("1", calculate("( 0 + 1 )"));
    }

    @Test
    void calculate_the_most_simple_subtract_operation(){
        assertEquals("-1", calculate("( 0 - 1 )"));
    }

    @Test
    void calculate_the_most_simple_multiply_operation(){
        assertEquals("2", calculate("( 2 * 1 )"));
    }

    @Test
    void calculate_the_most_simple_divide_operation(){
        assertEquals("3", calculate("( 6 / 2 )"));
    }

    @Test
    void calculate_the_most_simple_operation_with_brackets_inside(){
        assertEquals("2", calculate("( 1 + ( 0 + 1 ) )"));
    }

    @Test
    void calculate_the_another_operation_with_brackets_inside(){
        assertEquals("10", calculate("( ( 2 + 3 ) + ( 3 + 2 ) )"));
    }

    @Test
    void calculate_the_another_more_complex_operation_with_brackets_inside(){
        assertEquals("101", calculate("( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )"));
    }

    @Test
    void calculate_the_another_more_complex_operation_with_some_brackets_inside(){
        assertEquals("-165", calculate("( 5 * ( 4 * ( 3 * ( 2 * ( 1 * 9 ) / 8 - 7 ) + 6 ) ) )"));
    }

    @Test
    void throw_error_when_there_are_not_brackets_at_beginning_and_end(){
        assertEquals("Invalid record error", calculate(" 3 + ( 2 * 1 )"));
    }

    @Test
    void throw_error_when_there_is_no_even_number_of_brackets(){
        assertEquals("Invalid record error", calculate("( 3 + ( 2 * 1 )"));
    }

}

