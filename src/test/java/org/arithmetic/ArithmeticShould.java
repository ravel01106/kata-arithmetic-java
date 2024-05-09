package org.arithmetic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithmeticShould {
    // Devuelve un Error cuando no hay parentesis al principio y al final
    // Devolver un Error cuando no hay un número par de parentesis
    // Devolver 0 si no existe ningún número ni signos dentro de los parentesis
    // Si no hay ningun error calculamos la operación
    @Test
    void throw_error_when_there_are_not_brackets_at_beginning_and_at_end(){
        Arithmetic arithmetic = new Arithmetic();
        String result = arithmetic.calculate("3 + ( 2 * 1 )");
        assertEquals("Invalid record error", result);
    }
    @Test
    void throw_error_when_there_are_not_even_number_of_brackets(){
        Arithmetic arithmetic = new Arithmetic();
        String result = arithmetic.calculate("( 3 + ( 2 * 1 )");
        assertEquals("Invalid record error", result);
    }
    @Test
    void return_zero_when_only_there_are_brackets() {
        Arithmetic arithmetic = new Arithmetic();
        String result = arithmetic.calculate("((()()))");
        assertEquals("0", result);
    }

}