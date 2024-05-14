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
    // "( 0 + 1 )" -> "1"
    // "( 0 - 1 )" -> "-1"
    // "( 2 * 1 )" -> "2"
    // "( 6 / 2 )" -> "3"
    // "( 1 + ( 0 + 1 ) )" -> "2"
    // "( ( 2 + 3 ) + ( 3 + 2 ) )" -> "10"
    // "( 1 + ( ( 2 + 3 ) * (4 * 5) ) )" -> "101"
    private String calculate(String expression) {
        if (thereIsNotOperations(expression)){
            return "0";
        }
        String expressionCopy = expression;
        String operation = "";
        String resultOfOperation = "";
        

        while (isContainBrackets(expressionCopy)){
            operation = pickUpAnOperationWithBracketsFrom(expressionCopy);
            String[] expressionDivided = operation.split(" ");
            resultOfOperation = calculateOperationInBrackets(operation, expressionDivided);
            expressionCopy = expressionCopy.replace(operation,resultOfOperation);
        }
        if (isInteger(expressionCopy)){
            return removeCommasIn(expressionCopy);
        }
        return expressionCopy;
    }
    private static boolean isContainBrackets(String expression){
        return expression.contains("(") || expression.contains(")");
    }
    private static boolean isInteger(String expression){
        return expression.contains(".0");
    }
    private String removeCommasIn(String expression){
        int number = (int)Double.parseDouble(expression);
        return String.valueOf(number);
    }

    private static String calculateOperationInBrackets(String operation, String[] operationDivided){
        String result = "";
        String operationInBrackets = operation;
        while (thereIsAnOperation(operationDivided)){
            operationDivided = operationInBrackets.split(" ");
            String simpleOperation = formatSimpleOperationFrom(operationDivided);
            result = calculateResult(operationDivided);
            operationInBrackets = operationInBrackets.replace(simpleOperation, result);
            operationDivided = operationInBrackets.split(" ");
        }
        return result;
    }
    private static String formatSimpleOperationFrom(String[] expression){
        return expression[1] + " " + expression[2] + " " + expression[3];
    }

    private static boolean thereIsAnOperation(String[] operationDivided){
        return operationDivided.length > 3;
    }

    private static String pickUpAnOperationWithBracketsFrom(String expression){
        String allSignals = "+-*/";
        String[] expressionDivided = expression.split(" ");
        String operation = "";
        for (String sign: expressionDivided){
            if (sign.equals(")")){
                operation += ")";
                break;
            }else if (sign.equals("(")){
                if (operation.contains("(")){
                    operation = "";
                }
                operation += sign + " ";
            }else if (isNumeric(sign)){
                operation += sign + " ";
            }else if (allSignals.contains(sign)){
                operation+= sign + " ";
            }
        }
        //System.out.println(operation);
        return operation;
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

    private static boolean isNumeric(String letter){
        try{
           double num = Double.parseDouble(letter);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    private static boolean thereIsNotOperations(String expression) {
        return expression
                .replace('(', ' ')
                .replace(')', ' ')
                .trim().isEmpty();
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


}

