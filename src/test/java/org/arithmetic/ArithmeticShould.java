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
        int firstOperator = 0;
        int secondOperator = 0;
        String operator = "";
        String result = "";

        while (expressionCopy.contains("(") || expressionCopy.contains(")")){
            operation = giveNewExpression(expressionCopy);
            String[] operationSplit = operation.split(" ");
            firstOperator = Integer.parseInt(String.valueOf(operationSplit[1]));
            secondOperator = Integer.parseInt(String.valueOf(operationSplit[3]));
            operator = String.valueOf(operationSplit[2]);
            result = calculateOperationWith(firstOperator, secondOperator, operator);
            expressionCopy = expressionCopy.replace(operation,result);
        }
        return expressionCopy.trim();
    }
    private static String giveNewExpression (String expression){
        String operators = "+-*/";
        String[] expressionSplit = expression.split(" ");
        String operation = "";
        for (String letter: expressionSplit){
            if (letter.equals(")")){
                operation += ")";
                break;
            }else if (letter.equals("(")){
                if (operation.contains("(")){
                    operation = "";
                }
                operation += letter + " ";
            }else if (isNumeric(letter)){
                operation += letter + " ";
            }else if (operators.contains(letter)){
                operation+= letter + " ";
            }
        }
        //System.out.println(operation);
        return operation;
    }
    private static String calculateOperationWith(int firstOperator, int secondOperator, String operator){
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
           int num = Integer.parseInt(letter);
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


}

