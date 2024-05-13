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
    private String calculate(String expression) {
        if (thereIsNotOperations(expression)){
            return "0";
        }

        String operation = "( 0 + 1 )";
        int firstOperator = Integer.parseInt(String.valueOf(expression.charAt(2)));
        int secondOperator = Integer.parseInt(String.valueOf(expression.charAt(6)));
        String operator = String.valueOf(expression.charAt(4));
        if (operator.equals("+")){
            return String.valueOf(firstOperator + secondOperator);
        }else if (operator.equals("-")){
            return String.valueOf(firstOperator - secondOperator);
        }
        return String.valueOf(firstOperator * secondOperator);
    }

    private static boolean thereIsNotOperations(String expression) {
        return expression
                .replace('(', ' ')
                .replace(')', ' ')
                .trim().equals("");
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



}

